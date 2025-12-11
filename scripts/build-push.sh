#!/bin/bash
set -e

echo "=== CRM Application Docker Build and Push Script ==="
echo

# Get project name and sanitize
PROJECT_NAME="crm"
IMAGE_NAME=$(echo "$PROJECT_NAME" | tr '[:upper:]' '[:lower:]' | tr -cs 'a-z0-9' '-' | sed 's/^-*//;s/-*$//')

echo "Project: $PROJECT_NAME"
echo "Sanitized image name: $IMAGE_NAME"
echo

# Prompt for image tag
echo -n "Enter image tag (default: latest): "
read IMAGE_TAG
IMAGE_TAG=${IMAGE_TAG:-latest}
IMAGE_TAG=$(echo "$IMAGE_TAG" | tr '[:upper:]' '[:lower:]' | tr -cs 'a-z0-9.-' '-' | sed 's/^-*//;s/-*$//')
echo "Using tag: $IMAGE_TAG"
echo

# Registry selection
echo "Select container registry:"
echo "1. AWS ECR"
echo "2. Docker Hub"
echo -n "Enter your choice (1-2): "
read REGISTRY_CHOICE

case $REGISTRY_CHOICE in
  1)
    echo "=== AWS ECR Selected ==="
    echo -n "Enter AWS region (default: us-east-1): "
    read AWS_REGION
    AWS_REGION=${AWS_REGION:-us-east-1}
    
    echo -n "Enter ECR repository name (default: $IMAGE_NAME): "
    read ECR_REPO
    ECR_REPO=${ECR_REPO:-$IMAGE_NAME}
    
    # Get AWS account ID
    ACCOUNT_ID=$(aws sts get-caller-identity --query Account --output text)
    REGISTRY_URL="$ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com"
    FULL_IMAGE_NAME="$REGISTRY_URL/$ECR_REPO:$IMAGE_TAG"
    
    echo "Registry URL: $REGISTRY_URL"
    echo "Full image name: $FULL_IMAGE_NAME"
    echo
    
    # Login to ECR
    echo "Logging into AWS ECR..."
    aws ecr get-login-password --region $AWS_REGION | docker login --username AWS --password-stdin $REGISTRY_URL
    
    if [ $? -ne 0 ]; then
      echo "ECR login failed. Please check your AWS credentials and region."
      exit 1
    fi
    
    # Create ECR repository if it doesn't exist
    echo "Checking/creating ECR repository..."
    aws ecr describe-repositories --repository-names $ECR_REPO --region $AWS_REGION >/dev/null 2>&1 || {
      echo "Creating ECR repository: $ECR_REPO"
      aws ecr create-repository --repository-name $ECR_REPO --region $AWS_REGION
    }
    ;;
    
  2)
    echo "=== Docker Hub Selected ==="
    echo -n "Enter Docker Hub username: "
    read DOCKER_USERNAME
    echo -n "Enter Docker Hub password/token: "
    read -s DOCKER_PASSWORD
    echo
    
    FULL_IMAGE_NAME="$DOCKER_USERNAME/$IMAGE_NAME:$IMAGE_TAG"
    echo "Full image name: $FULL_IMAGE_NAME"
    echo
    
    # Login to Docker Hub
    echo "Logging into Docker Hub..."
    echo $DOCKER_PASSWORD | docker login --username $DOCKER_USERNAME --password-stdin
    
    if [ $? -ne 0 ]; then
      echo "Docker Hub login failed. Please check your credentials."
      exit 1
    fi
    ;;
    
  *)
    echo "Invalid choice. Exiting."
    exit 1
    ;;
esac

# Build the Docker image
echo "Building Docker image..."
echo "Command: docker build -t $FULL_IMAGE_NAME ."
docker build -t $FULL_IMAGE_NAME .

if [ $? -ne 0 ]; then
  echo "Docker build failed!"
  exit 1
fi

echo "Docker build completed successfully!"
echo

# Push the image
echo "Pushing image to registry..."
docker push $FULL_IMAGE_NAME

if [ $? -ne 0 ]; then
  echo "Docker push failed!"
  exit 1
fi

echo "\n=== Build and Push Completed Successfully! ==="
echo "Image: $FULL_IMAGE_NAME"
echo "You can now use this image in your ECS deployments."
echo