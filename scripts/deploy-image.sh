#!/bin/bash
set -e
set -o pipefail

echo "=== AWS ECS Fargate Deployment Script for CRM Application ==="
echo

# Prompt for deployment configuration
echo -n "Enter AWS region (default: us-east-1): "
read AWS_REGION
AWS_REGION=${AWS_REGION:-us-east-1}

echo -n "Enter ECS cluster name (default: crm-cluster): "
read CLUSTER_NAME
CLUSTER_NAME=${CLUSTER_NAME:-crm-cluster}

echo -n "Enter VPC ID: "
read VPC_ID
if [ -z "$VPC_ID" ]; then
  echo "VPC ID is required for Fargate deployment."
  exit 1
fi

echo -n "Enter subnet IDs (comma-separated, at least 2): "
read SUBNET_INPUT
if [ -z "$SUBNET_INPUT" ]; then
  echo "At least 2 subnet IDs are required for high availability."
  exit 1
fi

# Parse subnets
IFS=',' read -ra SUBNET_ARRAY <<< "$SUBNET_INPUT"
SUBNET_1=$(echo "${SUBNET_ARRAY[0]}" | xargs)
SUBNET_2=$(echo "${SUBNET_ARRAY[1]:-${SUBNET_ARRAY[0]}}" | xargs)

echo -n "Enter security group ID: "
read SECURITY_GROUP
if [ -z "$SECURITY_GROUP" ]; then
  echo "Security group ID is required."
  exit 1
fi

echo -n "Enter ECR image URI (e.g., 123456789.dkr.ecr.region.amazonaws.com/crm:latest): "
read IMAGE_URI
if [ -z "$IMAGE_URI" ]; then
  echo "Image URI is required."
  exit 1
fi

# Database configuration
echo "=== Database Configuration ==="
echo -n "Enter database host: "
read DB_HOST
if [ -z "$DB_HOST" ]; then
  echo "Database host is required."
  exit 1
fi

echo -n "Enter database username: "
read DB_USERNAME
if [ -z "$DB_USERNAME" ]; then
  echo "Database username is required."
  exit 1
fi

echo -n "Enter database password: "
read -s DB_PASSWORD
echo
if [ -z "$DB_PASSWORD" ]; then
  echo "Database password is required."
  exit 1
fi

# Load balancer configuration
echo -n "Do you need a load balancer for this service? (y/n): "
read NEED_LB

if [[ "$NEED_LB" =~ ^[Yy] ]]; then
  echo "Creating Application Load Balancer and Target Group..."
  
  # Create ALB
  ALB_ARN=$(aws elbv2 create-load-balancer \
    --name "crm-alb" \
    --subnets $SUBNET_1 $SUBNET_2 \
    --security-groups $SECURITY_GROUP \
    --region $AWS_REGION \
    --query 'LoadBalancers[0].LoadBalancerArn' \
    --output text)
  
  if [ $? -ne 0 ]; then
    echo "Failed to create Application Load Balancer"
    exit 1
  fi
  
  # Create Target Group with ip target type for Fargate
  TARGET_GROUP_ARN=$(aws elbv2 create-target-group \
    --name "crm-tg" \
    --protocol HTTP \
    --port 8080 \
    --vpc-id $VPC_ID \
    --target-type ip \
    --health-check-path "/appinfo/health" \
    --health-check-interval-seconds 30 \
    --health-check-timeout-seconds 5 \
    --healthy-threshold-count 2 \
    --unhealthy-threshold-count 3 \
    --region $AWS_REGION \
    --query 'TargetGroups[0].TargetGroupArn' \
    --output text)
  
  if [ $? -ne 0 ]; then
    echo "Failed to create Target Group"
    exit 1
  fi
  
  # Create listener
  aws elbv2 create-listener \
    --load-balancer-arn $ALB_ARN \
    --protocol HTTP \
    --port 80 \
    --default-actions Type=forward,TargetGroupArn=$TARGET_GROUP_ARN \
    --region $AWS_REGION >/dev/null
  
  echo "Load balancer created successfully"
  echo "Target Group ARN: $TARGET_GROUP_ARN"
fi

# Get AWS Account ID
ACCOUNT_ID=$(aws sts get-caller-identity --query Account --output text)
echo "AWS Account ID: $ACCOUNT_ID"
echo

# Check/create ECS cluster
echo "Checking ECS cluster..."
aws ecs describe-clusters --clusters $CLUSTER_NAME --region $AWS_REGION >/dev/null 2>&1 || {
  echo "Creating ECS cluster: $CLUSTER_NAME"
  aws ecs create-cluster --cluster-name $CLUSTER_NAME --region $AWS_REGION
}

# Create CloudWatch log group
echo "Creating CloudWatch log group..."
aws logs create-log-group --log-group-name "/ecs/crm" --region $AWS_REGION 2>/dev/null || true

# Prepare task definition
echo "Preparing task definition..."
cp ecs/task-definition.json /tmp/task-definition.json
sed -i "s|{{IMAGE_URI}}|$IMAGE_URI|g" /tmp/task-definition.json
sed -i "s|{{AWS_REGION}}|$AWS_REGION|g" /tmp/task-definition.json
sed -i "s|{{ACCOUNT_ID}}|$ACCOUNT_ID|g" /tmp/task-definition.json
sed -i "s|{{DB_HOST}}|$DB_HOST|g" /tmp/task-definition.json
sed -i "s|{{DB_USERNAME}}|$DB_USERNAME|g" /tmp/task-definition.json
sed -i "s|{{DB_PASSWORD}}|$DB_PASSWORD|g" /tmp/task-definition.json

# Register task definition
echo "Registering task definition..."
TASK_DEF_ARN=$(aws ecs register-task-definition \
  --cli-input-json file:///tmp/task-definition.json \
  --region $AWS_REGION \
  --query 'taskDefinition.taskDefinitionArn' \
  --output text)

if [ $? -ne 0 ]; then
  echo "Failed to register task definition"
  exit 1
fi

echo "Task definition registered: $TASK_DEF_ARN"

# Prepare service definition
echo "Preparing service definition..."
cp ecs/service-definition.json /tmp/service-definition.json
sed -i "s|{{CLUSTER_NAME}}|$CLUSTER_NAME|g" /tmp/service-definition.json
sed -i "s|{{SUBNET_1}}|$SUBNET_1|g" /tmp/service-definition.json
sed -i "s|{{SUBNET_2}}|$SUBNET_2|g" /tmp/service-definition.json
sed -i "s|{{SECURITY_GROUP}}|$SECURITY_GROUP|g" /tmp/service-definition.json

# Add load balancer configuration if needed
if [[ "$NEED_LB" =~ ^[Yy] ]]; then
  # Add loadBalancers section and healthCheckGracePeriodSeconds
  jq --arg tg_arn "$TARGET_GROUP_ARN" '.loadBalancers = [{"targetGroupArn": $tg_arn, "containerName": "crm", "containerPort": 8080}] | .healthCheckGracePeriodSeconds = 300' /tmp/service-definition.json > /tmp/service-definition-with-lb.json
  mv /tmp/service-definition-with-lb.json /tmp/service-definition.json
fi

# Check if service exists
SERVICE_NAME="crm-service"
EXISTING_SERVICE=$(aws ecs describe-services \
  --cluster $CLUSTER_NAME \
  --services $SERVICE_NAME \
  --region $AWS_REGION \
  --query 'services[?serviceName==`crm-service`].serviceName' \
  --output text 2>/dev/null || echo "")

if [ -n "$EXISTING_SERVICE" ] && [ "$EXISTING_SERVICE" != "None" ]; then
  echo "Updating existing service..."
  aws ecs update-service \
    --cluster $CLUSTER_NAME \
    --service $SERVICE_NAME \
    --task-definition "$TASK_DEF_ARN" \
    --region $AWS_REGION >/dev/null
else
  echo "Creating new service..."
  aws ecs create-service \
    --cli-input-json file:///tmp/service-definition.json \
    --region $AWS_REGION >/dev/null
fi

# Wait for service stability
echo "Waiting for service to become stable..."
aws ecs wait services-stable \
  --cluster $CLUSTER_NAME \
  --services $SERVICE_NAME \
  --region $AWS_REGION

# Verify deployment
echo "\n=== Deployment Status ==="
aws ecs describe-services \
  --cluster $CLUSTER_NAME \
  --services $SERVICE_NAME \
  --region $AWS_REGION \
  --query 'services[0].{ServiceName:serviceName,Status:status,RunningCount:runningCount,DesiredCount:desiredCount,TaskDefinition:taskDefinition}' \
  --output table

# Display access information
echo "\n=== Access Information ==="
echo "Application deployed successfully!"
echo "CloudWatch Logs: /ecs/crm"

if [[ "$NEED_LB" =~ ^[Yy] ]]; then
  ALB_DNS=$(aws elbv2 describe-load-balancers \
    --load-balancer-arns $ALB_ARN \
    --region $AWS_REGION \
    --query 'LoadBalancers[0].DNSName' \
    --output text)
  echo "Load Balancer URL: http://$ALB_DNS"
  echo "Health Check URL: http://$ALB_DNS/appinfo/health"
fi

echo "\nDeployment completed successfully!"
echo "\nTroubleshooting:"
echo "- Check CloudWatch logs at /ecs/crm for application logs"
echo "- Verify security group allows inbound traffic on port 8080"
echo "- Check database connectivity from the application"
echo "- Monitor ECS service events for any issues"