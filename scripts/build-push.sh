#!/bin/bash
set -e

echo "=== CRM Application - Docker Build and Push Script ==="
echo

# Project configuration
PROJECT_NAME="crm"
IMAGE_NAME=$(echo "$PROJECT_NAME" | tr '[:upper:]' '[:lower:]' | tr -cs 'a-z0-9' '-' | sed 's/^-*//;s/-*$//')

# Prompt for image tag
echo "Enter image tag (default: latest):"
read -r IMAGE_TAG
IMAGE_TAG=${IMAGE_TAG:-latest}
IMAGE_TAG=$(echo "$IMAGE_TAG" | tr '[:upper:]' '[:lower:]' | tr -cs 'a-z0-9.-' '-' | sed 's/^-*//;s/-*$//')

echo
echo "Select registry type:"
echo "1. AWS ECR"
echo "2. Docker Hub"
read -p "Enter choice (1-2): " REGISTRY_CHOICE

case $REGISTRY_CHOICE in
  1)
    echo "=== AWS ECR Configuration ==="
    read -p "Enter AWS region: " AWS_REGION
    read -p "Enter AWS Account ID: " AWS_ACCOUNT_ID
    read -p "Enter ECR repository name (default: $IMAGE_NAME): " ECR_REPO
    ECR_REPO=${ECR_REPO:-$IMAGE_NAME}
    
    REGISTRY_URL="$AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com"
    FULL_IMAGE_NAME="$REGISTRY_URL/$ECR_REPO:$IMAGE_TAG"
    
    echo "Logging into AWS ECR..."
    aws ecr get-login-password --region $AWS_REGION | docker login --username AWS --password-stdin $REGISTRY_URL
    
    echo "Checking if ECR repository exists..."
    aws ecr describe-repositories --repository-names $ECR_REPO --region $AWS_REGION >/dev/null 2>&1 || {
      echo "Creating ECR repository: $ECR_REPO"
      aws ecr create-repository --repository-name $ECR_REPO --region $AWS_REGION
    }
    ;;
  2)
    echo "=== Docker Hub Configuration ==="
    read -p "Enter Docker Hub username: " DOCKER_USERNAME
    read -s -p "Enter Docker Hub password/token: " DOCKER_PASSWORD
    echo
    
    FULL_IMAGE_NAME="$DOCKER_USERNAME/$IMAGE_NAME:$IMAGE_TAG"
    
    echo "Logging into Docker Hub..."
    echo $DOCKER_PASSWORD | docker login --username $DOCKER_USERNAME --password-stdin
    ;;
  *)
    echo "Invalid choice. Exiting."
    exit 1
    ;;
esac

echo
echo "Building Docker image: $FULL_IMAGE_NAME"
docker build -t $FULL_IMAGE_NAME .

echo "Pushing Docker image: $FULL_IMAGE_NAME"
docker push $FULL_IMAGE_NAME

echo
echo "=== Build and Push Completed Successfully ==="
echo "Image: $FULL_IMAGE_NAME"
echo "You can now use this image URI in your ECS deployment."