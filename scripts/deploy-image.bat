@echo off
setlocal enabledelayedexpansion

echo ===========================================
echo     CRM Application - ECS Deployment
echo ===========================================
echo.

REM Get deployment configuration from user
set /p AWS_REGION="Enter AWS region: "
set /p CLUSTER_NAME="Enter ECS cluster name: "
set /p VPC_ID="Enter VPC ID: "
set /p SUBNET_INPUT="Enter subnet IDs (comma-separated): "
set /p SECURITY_GROUP="Enter security group ID: "
set /p IMAGE_URI="Enter ECR image URI: "

REM Database configuration
set /p DB_HOST="Enter database host: "
set /p DB_USERNAME="Enter database username: "
set /p DB_PASSWORD="Enter database password: "

REM Parse subnets
for /f "tokens=1,2 delims=," %%a in ("!SUBNET_INPUT!") do (
    set SUBNET_1=%%a
    set SUBNET_2=%%b
)
set SUBNET_1=!SUBNET_1: =!
set SUBNET_2=!SUBNET_2: =!

REM Get AWS Account ID
for /f "tokens=*" %%i in ('aws sts get-caller-identity --query Account --output text') do set ACCOUNT_ID=%%i
echo Using AWS Account ID: !ACCOUNT_ID!

REM Check if cluster exists, create if not
echo Checking ECS cluster...
aws ecs describe-clusters --clusters !CLUSTER_NAME! --region !AWS_REGION! >nul 2>&1
if !ERRORLEVEL! neq 0 (
    echo Creating ECS cluster: !CLUSTER_NAME!
    aws ecs create-cluster --cluster-name !CLUSTER_NAME! --region !AWS_REGION!
)

REM Ask about load balancer
set /p NEED_LB="Do you need a load balancer for this service? (y/n): "

set TARGET_GROUP_ARN=
if /i "!NEED_LB!"=="y" (
    echo Creating Application Load Balancer and Target Group...
    
    REM Create ALB
    for /f "tokens=*" %%i in ('aws elbv2 create-load-balancer --name crm-alb --subnets !SUBNET_1! !SUBNET_2! --security-groups !SECURITY_GROUP! --region !AWS_REGION! --query "LoadBalancers[0].LoadBalancerArn" --output text') do set ALB_ARN=%%i
    
    REM Create Target Group with target-type ip
    for /f "tokens=*" %%i in ('aws elbv2 create-target-group --name crm-targets --protocol HTTP --port 8080 --vpc-id !VPC_ID! --target-type ip --health-check-path "/appinfo/health" --health-check-interval-seconds 30 --healthy-threshold-count 2 --unhealthy-threshold-count 5 --region !AWS_REGION! --query "TargetGroups[0].TargetGroupArn" --output text') do set TARGET_GROUP_ARN=%%i
    
    REM Create Listener
    aws elbv2 create-listener --load-balancer-arn !ALB_ARN! --protocol HTTP --port 80 --default-actions Type=forward,TargetGroupArn=!TARGET_GROUP_ARN! --region !AWS_REGION!
    
    echo Load balancer created with Target Group ARN: !TARGET_GROUP_ARN!
)

REM Create CloudWatch log group
echo Creating CloudWatch log group...
aws logs create-log-group --log-group-name "/ecs/crm" --region !AWS_REGION! >nul 2>&1

REM Replace placeholders in task definition
echo Preparing task definition...
copy ecs\task-definition.json ecs\task-definition-temp.json >nul
powershell -Command "(Get-Content ecs\task-definition-temp.json) -replace '{{IMAGE_URI}}', '!IMAGE_URI!' | Set-Content ecs\task-definition-temp.json"
powershell -Command "(Get-Content ecs\task-definition-temp.json) -replace '{{AWS_REGION}}', '!AWS_REGION!' | Set-Content ecs\task-definition-temp.json"
powershell -Command "(Get-Content ecs\task-definition-temp.json) -replace '{{ACCOUNT_ID}}', '!ACCOUNT_ID!' | Set-Content ecs\task-definition-temp.json"
powershell -Command "(Get-Content ecs\task-definition-temp.json) -replace '{{DB_HOST}}', '!DB_HOST!' | Set-Content ecs\task-definition-temp.json"
powershell -Command "(Get-Content ecs\task-definition-temp.json) -replace '{{DB_USERNAME}}', '!DB_USERNAME!' | Set-Content ecs\task-definition-temp.json"
powershell -Command "(Get-Content ecs\task-definition-temp.json) -replace '{{DB_PASSWORD}}', '!DB_PASSWORD!' | Set-Content ecs\task-definition-temp.json"

REM Register task definition
echo Registering task definition...
for /f "tokens=*" %%i in ('aws ecs register-task-definition --cli-input-json file://ecs/task-definition-temp.json --region !AWS_REGION! --query "taskDefinition.taskDefinitionArn" --output text') do set TASK_DEF_ARN=%%i
echo Task definition registered: !TASK_DEF_ARN!

REM Prepare service definition
copy ecs\service-definition.json ecs\service-definition-temp.json >nul
powershell -Command "(Get-Content ecs\service-definition-temp.json) -replace '{{CLUSTER_NAME}}', '!CLUSTER_NAME!' | Set-Content ecs\service-definition-temp.json"
powershell -Command "(Get-Content ecs\service-definition-temp.json) -replace '{{SUBNET_1}}', '!SUBNET_1!' | Set-Content ecs\service-definition-temp.json"
powershell -Command "(Get-Content ecs\service-definition-temp.json) -replace '{{SUBNET_2}}', '!SUBNET_2!' | Set-Content ecs\service-definition-temp.json"
powershell -Command "(Get-Content ecs\service-definition-temp.json) -replace '{{SECURITY_GROUP}}', '!SECURITY_GROUP!' | Set-Content ecs\service-definition-temp.json"

if /i "!NEED_LB!"=="y" (
    powershell -Command "(Get-Content ecs\service-definition-temp.json) -replace '{{TARGET_GROUP_ARN}}', '!TARGET_GROUP_ARN!' | Set-Content ecs\service-definition-temp.json"
) else (
    REM Remove loadBalancers section if no load balancer needed
    powershell -Command "$content = Get-Content ecs\service-definition-temp.json | Out-String; $content = $content -replace '(?s)\s*\"loadBalancers\":\s*\[.*?\],?', ''; $content = $content -replace '\s*\"healthCheckGracePeriodSeconds\":\s*\d+,?', ''; $content | Set-Content ecs\service-definition-temp.json"
)

REM Check if service exists
set SERVICE_NAME=crm-service
for /f "tokens=*" %%i in ('aws ecs describe-services --cluster !CLUSTER_NAME! --services !SERVICE_NAME! --region !AWS_REGION! --query "services[0].serviceName" --output text 2^>nul') do set EXISTING_SERVICE=%%i

if "!EXISTING_SERVICE!"=="None" (
    echo Creating new ECS service...
    aws ecs create-service --cli-input-json file://ecs/service-definition-temp.json --region !AWS_REGION!
) else (
    echo Updating existing ECS service...
    aws ecs update-service --cluster !CLUSTER_NAME! --service !SERVICE_NAME! --task-definition !TASK_DEF_ARN! --region !AWS_REGION!
)

REM Wait for service stability
echo Waiting for service to stabilize...
aws ecs wait services-stable --cluster !CLUSTER_NAME! --services !SERVICE_NAME! --region !AWS_REGION!

REM Show results
echo.
echo ===========================================
echo Deployment completed successfully!
echo ===========================================

aws ecs describe-services --cluster !CLUSTER_NAME! --services !SERVICE_NAME! --region !AWS_REGION! --query "services[0].{ServiceName:serviceName,Status:status,RunningCount:runningCount,DesiredCount:desiredCount}"

if /i "!NEED_LB!"=="y" (
    for /f "tokens=*" %%i in ('aws elbv2 describe-load-balancers --load-balancer-arns !ALB_ARN! --region !AWS_REGION! --query "LoadBalancers[0].DNSName" --output text') do set ALB_DNS=%%i
    echo.
    echo Application URL: http://!ALB_DNS!
)

echo.
echo CloudWatch logs: /ecs/crm
echo.
echo Troubleshooting:
echo - Check ECS Console for task status
echo - View logs in CloudWatch: /ecs/crm
echo - Ensure security groups allow traffic on port 8080
echo - Verify database connectivity from ECS tasks

REM Cleanup temporary files
del ecs\task-definition-temp.json >nul 2>&1
del ecs\service-definition-temp.json >nul 2>&1

pause