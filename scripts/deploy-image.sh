#!/bin/bash
set -e
set -o pipefail

echo "=== CRM Application - ECS Fargate Deployment ==="
echo

# Prompt for deployment configuration
read -p "Enter AWS region: " AWS_REGION
read -p "Enter ECS cluster name: " CLUSTER_NAME
read -p "Enter VPC ID: " VPC_ID
read -p "Enter subnet IDs (comma-separated, at least 2): " SUBNET_INPUT
read -p "Enter security group ID: " SECURITY_GROUP
read -p "Enter ECR image URI: " IMAGE_URI
read -p "Enter database endpoint (RDS): " RDS_ENDPOINT
read -p "Enter database username: " DB_USERNAME
read -s -p "Enter database password: " DB_PASSWORD
echo

# Parse subnets
IFS=',' read -ra SUBNETS <<< "$SUBNET_INPUT"
SUBNET_1=$(echo ${SUBNETS[0]} | xargs)
SUBNET_2=$(echo ${SUBNETS[1]} | xargs)

# Get AWS Account ID
ACCOUNT_ID=$(aws sts get-caller-identity --query Account --output text)
echo "AWS Account ID: $ACCOUNT_ID"

# Check if cluster exists, create if not
echo "Checking ECS cluster..."
aws ecs describe-clusters --clusters $CLUSTER_NAME --region $AWS_REGION >/dev/null 2>&1 || {
  echo "Creating ECS cluster: $CLUSTER_NAME"
  aws ecs create-cluster --cluster-name $CLUSTER_NAME --region $AWS_REGION
}

# Create CloudWatch log group
echo "Creating CloudWatch log group..."
aws logs create-log-group --log-group-name "/ecs/crm" --region $AWS_REGION 2>/dev/null || echo "Log group already exists"

# Ask about load balancer
echo
read -p "Do you need a load balancer for this service? (y/n): " NEED_LB

if [[ $NEED_LB =~ ^[Yy]$ ]]; then
  echo "Creating Application Load Balancer..."
  
  # Create ALB
  ALB_ARN=$(aws elbv2 create-load-balancer \
    --name crm-alb \
    --subnets $SUBNET_1 $SUBNET_2 \
    --security-groups $SECURITY_GROUP \
    --region $AWS_REGION \
    --query 'LoadBalancers[0].LoadBalancerArn' --output text)
  
  # Create target group
  TARGET_GROUP_ARN=$(aws elbv2 create-target-group \
    --name crm-tg \
    --protocol HTTP \
    --port 8080 \
    --vpc-id $VPC_ID \
    --target-type ip \
    --health-check-path "/appinfo/health" \
    --region $AWS_REGION \
    --query 'TargetGroups[0].TargetGroupArn' --output text)
  
  # Create listener
  aws elbv2 create-listener \
    --load-balancer-arn $ALB_ARN \
    --protocol HTTP \
    --port 80 \
    --default-actions Type=forward,TargetGroupArn=$TARGET_GROUP_ARN \
    --region $AWS_REGION >/dev/null
  
  echo "Load balancer created with Target Group ARN: $TARGET_GROUP_ARN"
else
  # Remove loadBalancers from service definition
  TARGET_GROUP_ARN=""
fi

# Replace placeholders in task definition
echo "Updating task definition..."
sed -i "s|{{IMAGE_URI}}|$IMAGE_URI|g" ecs/task-definition.json
sed -i "s|{{AWS_REGION}}|$AWS_REGION|g" ecs/task-definition.json
sed -i "s|{{ACCOUNT_ID}}|$ACCOUNT_ID|g" ecs/task-definition.json
sed -i "s|{{RDS_ENDPOINT}}|$RDS_ENDPOINT|g" ecs/task-definition.json
sed -i "s|{{DB_USERNAME}}|$DB_USERNAME|g" ecs/task-definition.json
sed -i "s|{{DB_PASSWORD}}|$DB_PASSWORD|g" ecs/task-definition.json

# Register task definition
echo "Registering ECS task definition..."
TASK_DEF_ARN=$(aws ecs register-task-definition \
  --cli-input-json file://ecs/task-definition.json \
  --region $AWS_REGION \
  --query 'taskDefinition.taskDefinitionArn' --output text)

echo "Task definition registered: $TASK_DEF_ARN"

# Replace placeholders in service definition
echo "Updating service definition..."
sed -i "s|{{CLUSTER_NAME}}|$CLUSTER_NAME|g" ecs/service-definition.json
sed -i "s|{{SUBNET_1}}|$SUBNET_1|g" ecs/service-definition.json
sed -i "s|{{SUBNET_2}}|$SUBNET_2|g" ecs/service-definition.json
sed -i "s|{{SECURITY_GROUP}}|$SECURITY_GROUP|g" ecs/service-definition.json

if [[ $NEED_LB =~ ^[Yy]$ ]]; then
  sed -i "s|{{TARGET_GROUP_ARN}}|$TARGET_GROUP_ARN|g" ecs/service-definition.json
else
  # Remove loadBalancers section
  python3 -c "
import json
with open('ecs/service-definition.json', 'r') as f:
    data = json.load(f)
if 'loadBalancers' in data:
    del data['loadBalancers']
if 'healthCheckGracePeriodSeconds' in data:
    del data['healthCheckGracePeriodSeconds']
with open('ecs/service-definition.json', 'w') as f:
    json.dump(data, f, indent=2)
"
fi

# Check if service exists
echo "Checking if service exists..."
SERVICE_EXISTS=$(aws ecs describe-services \
  --cluster $CLUSTER_NAME \
  --services crm-service \
  --region $AWS_REGION \
  --query 'services[?serviceName==`crm-service`].serviceName' --output text 2>/dev/null || echo "")

if [ -z "$SERVICE_EXISTS" ]; then
  echo "Creating ECS service..."
  aws ecs create-service \
    --cli-input-json file://ecs/service-definition.json \
    --region $AWS_REGION >/dev/null
else
  echo "Updating existing ECS service..."
  aws ecs update-service \
    --cluster $CLUSTER_NAME \
    --service crm-service \
    --task-definition "$TASK_DEF_ARN" \
    --region $AWS_REGION >/dev/null
fi

echo "Waiting for service to become stable..."
aws ecs wait services-stable --cluster $CLUSTER_NAME --services crm-service --region $AWS_REGION

# Verify deployment
echo
echo "=== Deployment Status ==="
aws ecs describe-services \
  --cluster $CLUSTER_NAME \
  --services crm-service \
  --region $AWS_REGION \
  --query 'services[0].{ServiceName:serviceName,Status:status,RunningCount:runningCount,DesiredCount:desiredCount}' \
  --output table

if [[ $NEED_LB =~ ^[Yy]$ ]]; then
  echo
  echo "=== Load Balancer Information ==="
  ALB_DNS=$(aws elbv2 describe-load-balancers \
    --load-balancer-arns $ALB_ARN \
    --region $AWS_REGION \
    --query 'LoadBalancers[0].DNSName' --output text)
  echo "Application URL: http://$ALB_DNS"
  echo "Health Check URL: http://$ALB_DNS/appinfo/health"
fi

echo
echo "CloudWatch Logs: /ecs/crm"
echo "=== Deployment Completed Successfully ==="