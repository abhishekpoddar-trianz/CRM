#!/bin/bash
set -e

echo "==========================================="
echo "    CRM Application - Build & Push"
echo "==========================================="
echo

# Project configuration
PROJECT_NAME="crm"
IMAGE_NAME=$(echo "$PROJECT_NAME" | tr '[:upper:]' '[:lower:]' | tr -cs 'a-z0-9' '-' | sed 's/^-*//;s/-*$//')

# Get image tag from user
read -p "Enter image tag (default: latest): " IMAGE_TAG
IMAGE_TAG=${IMAGE_TAG:-latest}
IMAGE_TAG=$(echo "$IMAGE_TAG" | tr '[:upper:]' '[:lower:]' | tr -cs 'a-z0-9.-' '-' | sed 's/^-*//;s/-*$//')

echo
echo "Select registry:"
echo "1. AWS ECR"
echo "2. Docker Hub"
read -p "Enter choice (1-2): " REGISTRY_CHOICE

case $REGISTRY_CHOICE in
    1)
        echo "=== AWS ECR Setup ==="
        read -p "Enter AWS region: " AWS_REGION
        read -p "Enter ECR repository name: " ECR_REPO
        
        ACCOUNT_ID=$(aws sts get-caller-identity --query Account --output text)
        REGISTRY_URL="$ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com"
        FULL_IMAGE_NAME="$REGISTRY_URL/$ECR_REPO:$IMAGE_TAG"
        
        echo "Logging in to ECR..."
        aws ecr get-login-password --region $AWS_REGION | docker login --username AWS --password-stdin $REGISTRY_URL
        
        echo "Checking if ECR repository exists..."
        aws ecr describe-repositories --repository-names $ECR_REPO --region $AWS_REGION >/dev/null 2>&1 || {
            echo "Creating ECR repository..."
            aws ecr create-repository --repository-name $ECR_REPO --region $AWS_REGION
        }
        ;;
    2)
        echo "=== Docker Hub Setup ==="
        read -p "Enter Docker Hub username: " DOCKER_USERNAME
        read -s -p "Enter Docker Hub password: " DOCKER_PASSWORD
        echo
        
        FULL_IMAGE_NAME="$DOCKER_USERNAME/$IMAGE_NAME:$IMAGE_TAG"
        
        echo "Logging in to Docker Hub..."
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

echo
echo "Pushing image to registry..."
docker push $FULL_IMAGE_NAME

echo
echo "==========================================="
echo "Build and push completed successfully!"
echo "Image: $FULL_IMAGE_NAME"
echo "==========================================="