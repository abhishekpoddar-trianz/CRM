@echo off
setlocal enabledelayedexpansion

echo === CRM Application Docker Build and Push Script ===
echo.

REM Get project name and sanitize
set PROJECT_NAME=crm
set IMAGE_NAME=!PROJECT_NAME!
REM Convert to lowercase (simplified approach)
for %%i in (a b c d e f g h i j k l m n o p q r s t u v w x y z) do (
    set IMAGE_NAME=!IMAGE_NAME:%%i=%%i!
)
set IMAGE_NAME=!IMAGE_NAME: =-!
set IMAGE_NAME=!IMAGE_NAME:_=-!

echo Project: !PROJECT_NAME!
echo Sanitized image name: !IMAGE_NAME!
echo.

REM Prompt for image tag
set /p IMAGE_TAG="Enter image tag (default: latest): "
if "!IMAGE_TAG!"=="" set IMAGE_TAG=latest
echo Using tag: !IMAGE_TAG!
echo.

REM Registry selection
echo Select container registry:
echo 1. AWS ECR
echo 2. Docker Hub
set /p REGISTRY_CHOICE="Enter your choice (1-2): "

if "!REGISTRY_CHOICE!"=="1" (
    echo === AWS ECR Selected ===
    set /p AWS_REGION="Enter AWS region (default: us-east-1): "
    if "!AWS_REGION!"=="" set AWS_REGION=us-east-1
    
    set /p ECR_REPO="Enter ECR repository name (default: !IMAGE_NAME!): "
    if "!ECR_REPO!"=="" set ECR_REPO=!IMAGE_NAME!
    
    REM Get AWS account ID
    for /f "tokens=*" %%i in ('aws sts get-caller-identity --query Account --output text') do set ACCOUNT_ID=%%i
    set REGISTRY_URL=!ACCOUNT_ID!.dkr.ecr.!AWS_REGION!.amazonaws.com
    set FULL_IMAGE_NAME=!REGISTRY_URL!/!ECR_REPO!:!IMAGE_TAG!
    
    echo Registry URL: !REGISTRY_URL!
    echo Full image name: !FULL_IMAGE_NAME!
    echo.
    
    REM Login to ECR
    echo Logging into AWS ECR...
    aws ecr get-login-password --region !AWS_REGION! | docker login --username AWS --password-stdin !REGISTRY_URL!
    
    if !ERRORLEVEL! neq 0 (
        echo ECR login failed. Please check your AWS credentials and region.
        exit /b 1
    )
    
    REM Create ECR repository if it doesn't exist
    echo Checking/creating ECR repository...
    aws ecr describe-repositories --repository-names !ECR_REPO! --region !AWS_REGION! >nul 2>&1
    if !ERRORLEVEL! neq 0 (
        echo Creating ECR repository: !ECR_REPO!
        aws ecr create-repository --repository-name !ECR_REPO! --region !AWS_REGION!
    )
) else if "!REGISTRY_CHOICE!"=="2" (
    echo === Docker Hub Selected ===
    set /p DOCKER_USERNAME="Enter Docker Hub username: "
    set /p DOCKER_PASSWORD="Enter Docker Hub password/token: "
    
    set FULL_IMAGE_NAME=!DOCKER_USERNAME!/!IMAGE_NAME!:!IMAGE_TAG!
    echo Full image name: !FULL_IMAGE_NAME!
    echo.
    
    REM Login to Docker Hub
    echo Logging into Docker Hub...
    echo !DOCKER_PASSWORD! | docker login --username !DOCKER_USERNAME! --password-stdin
    
    if !ERRORLEVEL! neq 0 (
        echo Docker Hub login failed. Please check your credentials.
        exit /b 1
    )
) else (
    echo Invalid choice. Exiting.
    exit /b 1
)

REM Build the Docker image
echo Building Docker image...
echo Command: docker build -t !FULL_IMAGE_NAME! .
docker build -t !FULL_IMAGE_NAME! .

if !ERRORLEVEL! neq 0 (
    echo Docker build failed!
    exit /b 1
)

echo Docker build completed successfully!
echo.

REM Push the image
echo Pushing image to registry...
docker push !FULL_IMAGE_NAME!

if !ERRORLEVEL! neq 0 (
    echo Docker push failed!
    exit /b 1
)

echo.
echo === Build and Push Completed Successfully! ===
echo Image: !FULL_IMAGE_NAME!
echo You can now use this image in your ECS deployments.
echo.
pause