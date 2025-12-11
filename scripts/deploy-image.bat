@echo off
setlocal enabledelayedexpansion

echo === AWS ECS Fargate Deployment Script for CRM Application ===
echo.

REM Prompt for deployment configuration
set /p AWS_REGION="Enter AWS region (default: us-east-1): "
if "!AWS_REGION!"=="" set AWS_REGION=us-east-1

set /p CLUSTER_NAME="Enter ECS cluster name (default: crm-cluster): "
if "!CLUSTER_NAME!"=="" set CLUSTER_NAME=crm-cluster

set /p VPC_ID="Enter VPC ID: "
if "!VPC_ID!"=="" (
    echo VPC ID is required for Fargate deployment.
    exit /b 1
)

set /p SUBNET_INPUT="Enter subnet IDs (comma-separated, at least 2): "
if "!SUBNET_INPUT!"=="" (
    echo At least 2 subnet IDs are required for high availability.
    exit /b 1
)

REM Parse first two subnets
for /f "tokens=1,2 delims=," %%a in ("!SUBNET_INPUT!") do (
    set SUBNET_1=%%a
    set SUBNET_2=%%b
)
set SUBNET_1=!SUBNET_1: =!
if "!SUBNET_2!"=="" set SUBNET_2=!SUBNET_1!
set SUBNET_2=!SUBNET_2: =!

set /p SECURITY_GROUP="Enter security group ID: "
if "!SECURITY_GROUP!"=="" (
    echo Security group ID is required.
    exit /b 1
)

set /p IMAGE_URI="Enter ECR image URI: "
if "!IMAGE_URI!"=="" (
    echo Image URI is required.
    exit /b 1
)

REM Database configuration
echo === Database Configuration ===
set /p DB_HOST="Enter database host: "
if "!DB_HOST!"=="" (
    echo Database host is required.
    exit /b 1
)

set /p DB_USERNAME="Enter database username: "
if "!DB_USERNAME!"=="" (
    echo Database username is required.
    exit /b 1
)

set /p DB_PASSWORD="Enter database password: "
if "!DB_PASSWORD!"=="" (
    echo Database password is required.
    exit /b 1
)

REM Load balancer configuration
set /p NEED_LB="Do you need a load balancer for this service? (y/n): "

if /i "!NEED_LB!"=="y" (
    echo Creating Application Load Balancer and Target Group...
    
    REM Create ALB
    for /f "tokens=*" %%i in ('aws elbv2 create-load-balancer --name "crm-alb" --subnets !SUBNET_1! !SUBNET_2! --security-groups !SECURITY_GROUP! --region !AWS_REGION! --query "LoadBalancers[0].LoadBalancerArn" --output text') do set ALB_ARN=%%i
    
    if !ERRORLEVEL! neq 0 (
        echo Failed to create Application Load Balancer
        exit /b 1
    )
    
    REM Create Target Group
    for /f "tokens=*" %%i in ('aws elbv2 create-target-group --name "crm-tg" --protocol HTTP --port 8080 --vpc-id !VPC_ID! --target-type ip --health-check-path "/appinfo/health" --region !AWS_REGION! --query "TargetGroups[0].TargetGroupArn" --output text') do set TARGET_GROUP_ARN=%%i
    
    if !ERRORLEVEL! neq 0 (
        echo Failed to create Target Group
        exit /b 1
    )
    
    REM Create listener
    aws elbv2 create-listener --load-balancer-arn !ALB_ARN! --protocol HTTP --port 80 --default-actions Type=forward,TargetGroupArn=!TARGET_GROUP_ARN! --region !AWS_REGION! >nul
    
    echo Load balancer created successfully
    echo Target Group ARN: !TARGET_GROUP_ARN!
)

REM Get AWS Account ID
for /f "tokens=*" %%i in ('aws sts get-caller-identity --query Account --output text') do set ACCOUNT_ID=%%i
echo AWS Account ID: !ACCOUNT_ID!
echo.

REM Check/create ECS cluster
echo Checking ECS cluster...
aws ecs describe-clusters --clusters !CLUSTER_NAME! --region !AWS_REGION! >nul 2>&1
if !ERRORLEVEL! neq 0 (
    echo Creating ECS cluster: !CLUSTER_NAME!
    aws ecs create-cluster --cluster-name !CLUSTER_NAME! --region !AWS_REGION! >nul
)

REM Create CloudWatch log group
echo Creating CloudWatch log group...
aws logs create-log-group --log-group-name "/ecs/crm" --region !AWS_REGION! >nul 2>&1

REM Prepare task definition
echo Preparing task definition...
copy ecs\task-definition.json %TEMP%\task-definition.json >nul
powershell -Command "(Get-Content '%TEMP%\task-definition.json') -replace '{{IMAGE_URI}}', '!IMAGE_URI!' | Set-Content '%TEMP%\task-definition.json'"
powershell -Command "(Get-Content '%TEMP%\task-definition.json') -replace '{{AWS_REGION}}', '!AWS_REGION!' | Set-Content '%TEMP%\task-definition.json'"
powershell -Command "(Get-Content '%TEMP%\task-definition.json') -replace '{{ACCOUNT_ID}}', '!ACCOUNT_ID!' | Set-Content '%TEMP%\task-definition.json'"
powershell -Command "(Get-Content '%TEMP%\task-definition.json') -replace '{{DB_HOST}}', '!DB_HOST!' | Set-Content '%TEMP%\task-definition.json'"
powershell -Command "(Get-Content '%TEMP%\task-definition.json') -replace '{{DB_USERNAME}}', '!DB_USERNAME!' | Set-Content '%TEMP%\task-definition.json'"
powershell -Command "(Get-Content '%TEMP%\task-definition.json') -replace '{{DB_PASSWORD}}', '!DB_PASSWORD!' | Set-Content '%TEMP%\task-definition.json'"

REM Register task definition
echo Registering task definition...
for /f "tokens=*" %%i in ('aws ecs register-task-definition --cli-input-json file://%TEMP%/task-definition.json --region !AWS_REGION! --query "taskDefinition.taskDefinitionArn" --output text') do set TASK_DEF_ARN=%%i

if !ERRORLEVEL! neq 0 (
    echo Failed to register task definition
    exit /b 1
)

echo Task definition registered: !TASK_DEF_ARN!

REM Prepare service definition
echo Preparing service definition...
copy ecs\service-definition.json %TEMP%\service-definition.json >nul
powershell -Command "(Get-Content '%TEMP%\service-definition.json') -replace '{{CLUSTER_NAME}}', '!CLUSTER_NAME!' | Set-Content '%TEMP%\service-definition.json'"
powershell -Command "(Get-Content '%TEMP%\service-definition.json') -replace '{{SUBNET_1}}', '!SUBNET_1!' | Set-Content '%TEMP%\service-definition.json'"
powershell -Command "(Get-Content '%TEMP%\service-definition.json') -replace '{{SUBNET_2}}', '!SUBNET_2!' | Set-Content '%TEMP%\service-definition.json'"
powershell -Command "(Get-Content '%TEMP%\service-definition.json') -replace '{{SECURITY_GROUP}}', '!SECURITY_GROUP!' | Set-Content '%TEMP%\service-definition.json'"

REM Add load balancer configuration if needed
if /i "!NEED_LB!"=="y" (
    powershell -Command "$json = Get-Content '%TEMP%\service-definition.json' | ConvertFrom-Json; $json | Add-Member -Type NoteProperty -Name 'loadBalancers' -Value @(@{targetGroupArn='!TARGET_GROUP_ARN!';containerName='crm';containerPort=8080}) -Force; $json | Add-Member -Type NoteProperty -Name 'healthCheckGracePeriodSeconds' -Value 300 -Force; $json | ConvertTo-Json -Depth 10 | Set-Content '%TEMP%\service-definition.json'"
)

REM Check if service exists
set SERVICE_NAME=crm-service
for /f "tokens=*" %%i in ('aws ecs describe-services --cluster !CLUSTER_NAME! --services !SERVICE_NAME! --region !AWS_REGION! --query "services[?serviceName==\`crm-service\`].serviceName" --output text 2^>nul') do set EXISTING_SERVICE=%%i

if not "!EXISTING_SERVICE!"=="" if not "!EXISTING_SERVICE!"=="None" (
    echo Updating existing service...
    aws ecs update-service --cluster !CLUSTER_NAME! --service !SERVICE_NAME! --task-definition "!TASK_DEF_ARN!" --region !AWS_REGION! >nul
) else (
    echo Creating new service...
    aws ecs create-service --cli-input-json file://%TEMP%/service-definition.json --region !AWS_REGION! >nul
)

REM Wait for service stability
echo Waiting for service to become stable...
aws ecs wait services-stable --cluster !CLUSTER_NAME! --services !SERVICE_NAME! --region !AWS_REGION!

REM Verify deployment
echo.
echo === Deployment Status ===
aws ecs describe-services --cluster !CLUSTER_NAME! --services !SERVICE_NAME! --region !AWS_REGION! --query "services[0].{ServiceName:serviceName,Status:status,RunningCount:runningCount,DesiredCount:desiredCount,TaskDefinition:taskDefinition}" --output table

REM Display access information
echo.
echo === Access Information ===
echo Application deployed successfully!
echo CloudWatch Logs: /ecs/crm

if /i "!NEED_LB!"=="y" (
    for /f "tokens=*" %%i in ('aws elbv2 describe-load-balancers --load-balancer-arns !ALB_ARN! --region !AWS_REGION! --query "LoadBalancers[0].DNSName" --output text') do set ALB_DNS=%%i
    echo Load Balancer URL: http://!ALB_DNS!
    echo Health Check URL: http://!ALB_DNS!/appinfo/health
)

echo.
echo Deployment completed successfully!
echo.
echo Troubleshooting:
echo - Check CloudWatch logs at /ecs/crm for application logs
echo - Verify security group allows inbound traffic on port 8080
echo - Check database connectivity from the application
echo - Monitor ECS service events for any issues
echo.
pause