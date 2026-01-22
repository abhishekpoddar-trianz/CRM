#!/bin/bash
set -e
set -o pipefail

echo "============================================"
echo "  CRM Application - ECS Fargate Deployment"
echo "============================================"
echo ""

# Configuration
SERVICE_NAME="crm-app-service"
TASK_FAMILY="crm-app-task"
CONTAINER_NAME="crm-app"
APP_PORT=8080

# Prompt for AWS configuration
read -p "Enter AWS Region [us-east-1]: " AWS_REGION
AWS_REGION=${AWS_REGION:-us-east-1}

read -p "Enter ECS Cluster Name [crm-cluster]: " CLUSTER_NAME
CLUSTER_NAME=${CLUSTER_NAME:-crm-cluster}

echo ""
echo "--- Network Configuration ---"
read -p "Enter VPC ID: " VPC_ID
if [ -z "$VPC_ID" ]; then
    echo "Error: VPC ID is required"
    exit 1
fi

read -p "Enter Subnet IDs (comma-separated, at least 2): " SUBNETS_INPUT
if [ -z "$SUBNETS_INPUT" ]; then
    echo "Error: At least 2 subnet IDs are required for high availability"
    exit 1
fi

# Parse subnets
IFS=',' read -ra SUBNET_ARRAY <<< "$SUBNETS_INPUT"
SUBNET_1=$(echo "${SUBNET_ARRAY[0]}" | xargs)
SUBNET_2=$(echo "${SUBNET_ARRAY[1]:-$SUBNET_1}" | xargs)

read -p "Enter Security Group ID: " SECURITY_GROUP
if [ -z "$SECURITY_GROUP" ]; then
    echo "Error: Security Group ID is required"
    exit 1
fi

echo ""
echo "--- Container Image ---"
read -p "Enter ECR Image URI (e.g., 123456789.dkr.ecr.us-east-1.amazonaws.com/crm-app:latest): " IMAGE_URI
if [ -z "$IMAGE_URI" ]; then
    echo "Error: Image URI is required"
    exit 1
fi

echo ""
echo "--- Database Configuration ---"
read -p "Enter Database Host: " DB_HOST
DB_HOST=${DB_HOST:-localhost}

read -p "Enter Database Port [3306]: " DB_PORT
DB_PORT=${DB_PORT:-3306}

read -p "Enter Database Name [crm]: " DB_NAME
DB_NAME=${DB_NAME:-crm}

read -p "Enter Database Username: " DB_USERNAME
if [ -z "$DB_USERNAME" ]; then
    echo "Error: Database username is required"
    exit 1
fi

read -sp "Enter Database Password: " DB_PASSWORD
echo ""
if [ -z "$DB_PASSWORD" ]; then
    echo "Error: Database password is required"
    exit 1
fi

# Get AWS Account ID
echo ""
echo "Retrieving AWS Account ID..."
ACCOUNT_ID=$(aws sts get-caller-identity --query Account --output text)
echo "AWS Account ID: $ACCOUNT_ID"

# Check/Create ECS Cluster
echo ""
echo "Checking ECS cluster..."
aws ecs describe-clusters --clusters "$CLUSTER_NAME" --region "$AWS_REGION" >/dev/null 2>&1 || {
    echo "Cluster does not exist. Creating ECS cluster: $CLUSTER_NAME"
    aws ecs create-cluster --cluster-name "$CLUSTER_NAME" --region "$AWS_REGION"
    echo "Cluster created successfully"
}

# Create CloudWatch Log Group
echo ""
echo "Creating CloudWatch log group..."
aws logs create-log-group --log-group-name "/ecs/crm-app" --region "$AWS_REGION" 2>/dev/null || echo "Log group already exists"

# Load Balancer Configuration
echo ""
read -p "Do you need a load balancer for this service? (y/n) [y]: " NEED_LB
NEED_LB=${NEED_LB:-y}

if [[ "$NEED_LB" =~ ^[Yy]$ ]]; then
    echo "Creating Application Load Balancer and Target Group..."
    
    # Create ALB
    ALB_NAME="crm-app-alb"
    echo "Creating ALB: $ALB_NAME"
    ALB_ARN=$(aws elbv2 create-load-balancer \
        --name "$ALB_NAME" \
        --subnets "$SUBNET_1" "$SUBNET_2" \
        --security-groups "$SECURITY_GROUP" \
        --scheme internet-facing \
        --type application \
        --ip-address-type ipv4 \
        --region "$AWS_REGION" \
        --query 'LoadBalancers[0].LoadBalancerArn' \
        --output text 2>/dev/null || aws elbv2 describe-load-balancers --names "$ALB_NAME" --region "$AWS_REGION" --query 'LoadBalancers[0].LoadBalancerArn' --output text)
    
    echo "ALB ARN: $ALB_ARN"
    
    # Get ALB DNS
    ALB_DNS=$(aws elbv2 describe-load-balancers --load-balancer-arns "$ALB_ARN" --region "$AWS_REGION" --query 'LoadBalancers[0].DNSName' --output text)
    
    # Create Target Group with target-type ip (required for Fargate)
    TG_NAME="crm-app-tg"
    echo "Creating Target Group: $TG_NAME"
    TARGET_GROUP_ARN=$(aws elbv2 create-target-group \
        --name "$TG_NAME" \
        --protocol HTTP \
        --port "$APP_PORT" \
        --vpc-id "$VPC_ID" \
        --target-type ip \
        --health-check-enabled \
        --health-check-protocol HTTP \
        --health-check-path "/appinfo/health" \
        --health-check-interval-seconds 30 \
        --health-check-timeout-seconds 5 \
        --healthy-threshold-count 2 \
        --unhealthy-threshold-count 3 \
        --region "$AWS_REGION" \
        --query 'TargetGroups[0].TargetGroupArn' \
        --output text 2>/dev/null || aws elbv2 describe-target-groups --names "$TG_NAME" --region "$AWS_REGION" --query 'TargetGroups[0].TargetGroupArn' --output text)
    
    echo "Target Group ARN: $TARGET_GROUP_ARN"
    
    # Create Listener
    echo "Creating ALB Listener..."
    aws elbv2 create-listener \
        --load-balancer-arn "$ALB_ARN" \
        --protocol HTTP \
        --port 80 \
        --default-actions Type=forward,TargetGroupArn="$TARGET_GROUP_ARN" \
        --region "$AWS_REGION" >/dev/null 2>&1 || echo "Listener already exists"
    
    LB_CONFIG="yes"
else
    echo "Skipping load balancer configuration"
    TARGET_GROUP_ARN=""
    LB_CONFIG="no"
fi

# Prepare task definition
echo ""
echo "Preparing ECS task definition..."
cp ecs/task-definition.json /tmp/task-definition.json

sed -i "s|{{IMAGE_URI}}|$IMAGE_URI|g" /tmp/task-definition.json
sed -i "s|{{AWS_REGION}}|$AWS_REGION|g" /tmp/task-definition.json
sed -i "s|{{ACCOUNT_ID}}|$ACCOUNT_ID|g" /tmp/task-definition.json
sed -i "s|{{DB_HOST}}|$DB_HOST|g" /tmp/task-definition.json
sed -i "s|{{DB_PORT}}|$DB_PORT|g" /tmp/task-definition.json
sed -i "s|{{DB_NAME}}|$DB_NAME|g" /tmp/task-definition.json
sed -i "s|{{DB_USERNAME}}|$DB_USERNAME|g" /tmp/task-definition.json
sed -i "s|{{DB_PASSWORD}}|$DB_PASSWORD|g" /tmp/task-definition.json

# Register task definition
echo "Registering task definition..."
TASK_DEF_ARN=$(aws ecs register-task-definition \
    --cli-input-json file:///tmp/task-definition.json \
    --region "$AWS_REGION" \
    --query 'taskDefinition.taskDefinitionArn' \
    --output text)

echo "Task Definition ARN: $TASK_DEF_ARN"

# Prepare service definition
echo ""
echo "Preparing ECS service definition..."
cp ecs/service-definition.json /tmp/service-definition.json

sed -i "s|{{CLUSTER_NAME}}|$CLUSTER_NAME|g" /tmp/service-definition.json
sed -i "s|{{SUBNET_1}}|$SUBNET_1|g" /tmp/service-definition.json
sed -i "s|{{SUBNET_2}}|$SUBNET_2|g" /tmp/service-definition.json
sed -i "s|{{SECURITY_GROUP}}|$SECURITY_GROUP|g" /tmp/service-definition.json

if [ "$LB_CONFIG" = "yes" ]; then
    sed -i "s|{{TARGET_GROUP_ARN}}|$TARGET_GROUP_ARN|g" /tmp/service-definition.json
else
    # Remove loadBalancers section
    sed -i '/"loadBalancers"/,/\],/d' /tmp/service-definition.json
    sed -i '/"healthCheckGracePeriodSeconds"/d' /tmp/service-definition.json
fi

# Check if service exists
echo ""
echo "Checking if service exists..."
EXISTING_SERVICE=$(aws ecs describe-services \
    --cluster "$CLUSTER_NAME" \
    --services "$SERVICE_NAME" \
    --region "$AWS_REGION" \
    --query 'services[0].serviceName' \
    --output text 2>/dev/null)

if [ "$EXISTING_SERVICE" = "$SERVICE_NAME" ]; then
    echo "Service exists. Updating service..."
    aws ecs update-service \
        --cluster "$CLUSTER_NAME" \
        --service "$SERVICE_NAME" \
        --task-definition "$TASK_DEF_ARN" \
        --force-new-deployment \
        --region "$AWS_REGION" >/dev/null
    echo "Service updated successfully"
else
    echo "Service does not exist. Creating new service..."
    aws ecs create-service \
        --cli-input-json file:///tmp/service-definition.json \
        --region "$AWS_REGION" >/dev/null
    echo "Service created successfully"
fi

# Wait for service stability
echo ""
echo "Waiting for service to become stable (this may take a few minutes)..."
aws ecs wait services-stable \
    --cluster "$CLUSTER_NAME" \
    --services "$SERVICE_NAME" \
    --region "$AWS_REGION"

# Verify deployment
echo ""
echo "============================================"
echo "Deployment completed successfully!"
echo "============================================"
echo ""
echo "Service Details:"
aws ecs describe-services \
    --cluster "$CLUSTER_NAME" \
    --services "$SERVICE_NAME" \
    --region "$AWS_REGION" \
    --query 'services[0].[serviceName,status,runningCount,desiredCount]' \
    --output table

if [ "$LB_CONFIG" = "yes" ]; then
    echo ""
    echo "Application URL: http://$ALB_DNS"
    echo "Health Check: http://$ALB_DNS/appinfo/health"
fi

echo ""
echo "CloudWatch Logs: /ecs/crm-app"
echo "Region: $AWS_REGION"
echo ""
echo "To view logs, run:"
echo "aws logs tail /ecs/crm-app --follow --region $AWS_REGION"
echo ""

# Cleanup temp files
rm -f /tmp/task-definition.json /tmp/service-definition.json

echo "Deployment complete!"
echo ""