#!/bin/bash
set -e
set -o pipefail

echo "==========================================="
echo "    CRM Application - ECS Deployment"
echo "==========================================="
echo

# Get deployment configuration from user
read -p "Enter AWS region: " AWS_REGION
read -p "Enter ECS cluster name: " CLUSTER_NAME
read -p "Enter VPC ID: " VPC_ID
read -p "Enter subnet IDs (comma-separated): " SUBNET_INPUT
read -p "Enter security group ID: " SECURITY_GROUP
read -p "Enter ECR image URI: " IMAGE_URI

# Database configuration
read -p "Enter database host: " DB_HOST
read -p "Enter database username: " DB_USERNAME
read -s -p "Enter database password: " DB_PASSWORD
echo

# Convert comma-separated subnets to array
IFS=',' read -ra SUBNETS <<< "$SUBNET_INPUT"
SUBNET_1=${SUBNETS[0]// /}
SUBNET_2=${SUBNETS[1]// /}

# Get AWS Account ID
ACCOUNT_ID=$(aws sts get-caller-identity --query Account --output text)
echo "Using AWS Account ID: $ACCOUNT_ID"

# Check if cluster exists, create if not
echo "Checking ECS cluster..."
aws ecs describe-clusters --clusters $CLUSTER_NAME --region $AWS_REGION >/dev/null 2>&1 || {
    echo "Creating ECS cluster: $CLUSTER_NAME"
    aws ecs create-cluster --cluster-name $CLUSTER_NAME --region $AWS_REGION
}

# Ask about load balancer
read -p "Do you need a load balancer for this service? (y/n): " NEED_LB

TARGET_GROUP_ARN=""
if [[ $NEED_LB =~ ^[Yy]$ ]]; then
    echo "Creating Application Load Balancer and Target Group..."
    
    # Create ALB
    ALB_ARN=$(aws elbv2 create-load-balancer \
        --name crm-alb \
        --subnets $SUBNET_1 $SUBNET_2 \
        --security-groups $SECURITY_GROUP \
        --region $AWS_REGION \
        --query 'LoadBalancers[0].LoadBalancerArn' \
        --output text)
    
    # Create Target Group with target-type ip
    TARGET_GROUP_ARN=$(aws elbv2 create-target-group \
        --name crm-targets \
        --protocol HTTP \
        --port 8080 \
        --vpc-id $VPC_ID \
        --target-type ip \
        --health-check-path "/appinfo/health" \
        --health-check-interval-seconds 30 \
        --healthy-threshold-count 2 \
        --unhealthy-threshold-count 5 \
        --region $AWS_REGION \
        --query 'TargetGroups[0].TargetGroupArn' \
        --output text)
    
    # Create Listener
    aws elbv2 create-listener \
        --load-balancer-arn $ALB_ARN \
        --protocol HTTP \
        --port 80 \
        --default-actions Type=forward,TargetGroupArn=$TARGET_GROUP_ARN \
        --region $AWS_REGION
    
    echo "Load balancer created with Target Group ARN: $TARGET_GROUP_ARN"
fi

# Create CloudWatch log group
echo "Creating CloudWatch log group..."
aws logs create-log-group --log-group-name "/ecs/crm" --region $AWS_REGION 2>/dev/null || echo "Log group already exists"

# Replace placeholders in task definition
echo "Preparing task definition..."
cp ecs/task-definition.json ecs/task-definition-temp.json
sed -i "s|{{IMAGE_URI}}|$IMAGE_URI|g" ecs/task-definition-temp.json
sed -i "s|{{AWS_REGION}}|$AWS_REGION|g" ecs/task-definition-temp.json
sed -i "s|{{ACCOUNT_ID}}|$ACCOUNT_ID|g" ecs/task-definition-temp.json
sed -i "s|{{DB_HOST}}|$DB_HOST|g" ecs/task-definition-temp.json
sed -i "s|{{DB_USERNAME}}|$DB_USERNAME|g" ecs/task-definition-temp.json
sed -i "s|{{DB_PASSWORD}}|$DB_PASSWORD|g" ecs/task-definition-temp.json

# Register task definition
echo "Registering task definition..."
TASK_DEF_ARN=$(aws ecs register-task-definition \
    --cli-input-json file://ecs/task-definition-temp.json \
    --region $AWS_REGION \
    --query 'taskDefinition.taskDefinitionArn' \
    --output text)

echo "Task definition registered: $TASK_DEF_ARN"

# Prepare service definition
cp ecs/service-definition.json ecs/service-definition-temp.json
sed -i "s|{{CLUSTER_NAME}}|$CLUSTER_NAME|g" ecs/service-definition-temp.json
sed -i "s|{{SUBNET_1}}|$SUBNET_1|g" ecs/service-definition-temp.json
sed -i "s|{{SUBNET_2}}|$SUBNET_2|g" ecs/service-definition-temp.json
sed -i "s|{{SECURITY_GROUP}}|$SECURITY_GROUP|g" ecs/service-definition-temp.json

if [[ $NEED_LB =~ ^[Yy]$ ]]; then
    sed -i "s|{{TARGET_GROUP_ARN}}|$TARGET_GROUP_ARN|g" ecs/service-definition-temp.json
else
    # Remove loadBalancers section if no load balancer needed
    sed -i '/"loadBalancers":/,/],/d' ecs/service-definition-temp.json
    sed -i '/"healthCheckGracePeriodSeconds":/d' ecs/service-definition-temp.json
fi

# Check if service exists
SERVICE_NAME="crm-service"
EXISTING_SERVICE=$(aws ecs describe-services \
    --cluster $CLUSTER_NAME \
    --services $SERVICE_NAME \
    --region $AWS_REGION \
    --query 'services[0].serviceName' \
    --output text 2>/dev/null || echo "None")

if [ "$EXISTING_SERVICE" = "None" ] || [ "$EXISTING_SERVICE" = "null" ]; then
    echo "Creating new ECS service..."
    aws ecs create-service \
        --cli-input-json file://ecs/service-definition-temp.json \
        --region $AWS_REGION
else
    echo "Updating existing ECS service..."
    aws ecs update-service \
        --cluster $CLUSTER_NAME \
        --service $SERVICE_NAME \
        --task-definition $TASK_DEF_ARN \
        --region $AWS_REGION
fi

# Wait for service stability
echo "Waiting for service to stabilize..."
aws ecs wait services-stable --cluster $CLUSTER_NAME --services $SERVICE_NAME --region $AWS_REGION

# Get service details
echo "\n==========================================="
echo "Deployment completed successfully!"
echo "==========================================="

# Show service status
aws ecs describe-services \
    --cluster $CLUSTER_NAME \
    --services $SERVICE_NAME \
    --region $AWS_REGION \
    --query 'services[0].{ServiceName:serviceName,Status:status,RunningCount:runningCount,DesiredCount:desiredCount}'

if [[ $NEED_LB =~ ^[Yy]$ ]]; then
    ALB_DNS=$(aws elbv2 describe-load-balancers \
        --load-balancer-arns $ALB_ARN \
        --region $AWS_REGION \
        --query 'LoadBalancers[0].DNSName' \
        --output text)
    echo "\nApplication URL: http://$ALB_DNS"
fi

echo "\nCloudWatch logs: /ecs/crm"
echo "\nTroubleshooting:"
echo "- Check ECS Console for task status"
echo "- View logs in CloudWatch: /ecs/crm"
echo "- Ensure security groups allow traffic on port 8080"
echo "- Verify database connectivity from ECS tasks"

# Cleanup temporary files
rm -f ecs/task-definition-temp.json ecs/service-definition-temp.json