@echo off
setlocal enabledelayedexpansion

echo === CRM Application - ECS Fargate Deployment ===
echo.

rem Prompt for deployment configuration
set /p AWS_REGION="Enter AWS region: "
set /p CLUSTER_NAME="Enter ECS cluster name: "
set /p VPC_ID="Enter VPC ID: "
set /p SUBNET_INPUT="Enter subnet IDs (comma-separated, at least 2): "
set /p SECURITY_GROUP="Enter security group ID: "
set /p IMAGE_URI="Enter ECR image URI: "
set /p RDS_ENDPOINT="Enter database endpoint (RDS): "
set /p DB_USERNAME="Enter database username: "
set /p DB_PASSWORD="Enter database password: "

rem Parse subnets (simplified for batch)
for /f "tokens=1,2 delims=," %%a in ("!SUBNET_INPUT!") do (
    set SUBNET_1=%%a
    set SUBNET_2=%%b
)
set SUBNET_1=!SUBNET_1: =!
set SUBNET_2=!SUBNET_2: =!

rem Get AWS Account ID
for /f "tokens=*" %%i in ('aws sts get-caller-identity --query Account --output text') do set ACCOUNT_ID=%%i
echo AWS Account ID: !ACCOUNT_ID!

rem Check if cluster exists, create if not
echo Checking ECS cluster...
aws ecs describe-clusters --clusters !CLUSTER_NAME! --region !AWS_REGION! >nul 2>&1
if !ERRORLEVEL! neq 0 (
    echo Creating ECS cluster: !CLUSTER_NAME!
    aws ecs create-cluster --cluster-name !CLUSTER_NAME! --region !AWS_REGION!
)

rem Create CloudWatch log group
echo Creating CloudWatch log group...
aws logs create-log-group --log-group-name "/ecs/crm" --region !AWS_REGION! 2>nul

rem Ask about load balancer
echo.
set /p NEED_LB="Do you need a load balancer for this service? (y/n): "

if /i "!NEED_LB!"=="y" (
    echo Creating Application Load Balancer...
    
    rem Create ALB
    for /f "tokens=*" %%i in ('aws elbv2 create-load-balancer --name crm-alb --subnets !SUBNET_1! !SUBNET_2! --security-groups !SECURITY_GROUP! --region !AWS_REGION! --query "LoadBalancers[0].LoadBalancerArn" --output text') do set ALB_ARN=%%i
    
    rem Create target group
    for /f "tokens=*" %%i in ('aws elbv2 create-target-group --name crm-tg --protocol HTTP --port 8080 --vpc-id !VPC_ID! --target-type ip --health-check-path "/appinfo/health" --region !AWS_REGION! --query "TargetGroups[0].TargetGroupArn" --output text') do set TARGET_GROUP_ARN=%%i
    
    rem Create listener
    aws elbv2 create-listener --load-balancer-arn !ALB_ARN! --protocol HTTP --port 80 --default-actions Type=forward,TargetGroupArn=!TARGET_GROUP_ARN! --region !AWS_REGION! >nul
    
    echo Load balancer created with Target Group ARN: !TARGET_GROUP_ARN!
) else (
    set TARGET_GROUP_ARN=
)

rem Replace placeholders in task definition using PowerShell
echo Updating task definition...
powershell -Command "(Get-Content 'ecs/task-definition.json') -replace '{{IMAGE_URI}}', '!IMAGE_URI!' | Set-Content 'ecs/task-definition.json'"
powershell -Command "(Get-Content 'ecs/task-definition.json') -replace '{{AWS_REGION}}', '!AWS_REGION!' | Set-Content 'ecs/task-definition.json'"
powershell -Command "(Get-Content 'ecs/task-definition.json') -replace '{{ACCOUNT_ID}}', '!ACCOUNT_ID!' | Set-Content 'ecs/task-definition.json'"
powershell -Command "(Get-Content 'ecs/task-definition.json') -replace '{{RDS_ENDPOINT}}', '!RDS_ENDPOINT!' | Set-Content 'ecs/task-definition.json'"
powershell -Command "(Get-Content 'ecs/task-definition.json') -replace '{{DB_USERNAME}}', '!DB_USERNAME!' | Set-Content 'ecs/task-definition.json'"
powershell -Command "(Get-Content 'ecs/task-definition.json') -replace '{{DB_PASSWORD}}', '!DB_PASSWORD!' | Set-Content 'ecs/task-definition.json'"

rem Register task definition
echo Registering ECS task definition...
for /f "tokens=*" %%i in ('aws ecs register-task-definition --cli-input-json file://ecs/task-definition.json --region !AWS_REGION! --query "taskDefinition.taskDefinitionArn" --output text') do set TASK_DEF_ARN=%%i
echo Task definition registered: !TASK_DEF_ARN!

rem Replace placeholders in service definition
echo Updating service definition...
powershell -Command "(Get-Content 'ecs/service-definition.json') -replace '{{CLUSTER_NAME}}', '!CLUSTER_NAME!' | Set-Content 'ecs/service-definition.json'"
powershell -Command "(Get-Content 'ecs/service-definition.json') -replace '{{SUBNET_1}}', '!SUBNET_1!' | Set-Content 'ecs/service-definition.json'"
powershell -Command "(Get-Content 'ecs/service-definition.json') -replace '{{SUBNET_2}}', '!SUBNET_2!' | Set-Content 'ecs/service-definition.json'"
powershell -Command "(Get-Content 'ecs/service-definition.json') -replace '{{SECURITY_GROUP}}', '!SECURITY_GROUP!' | Set-Content 'ecs/service-definition.json'"

if /i "!NEED_LB!"=="y" (
    powershell -Command "(Get-Content 'ecs/service-definition.json') -replace '{{TARGET_GROUP_ARN}}', '!TARGET_GROUP_ARN!' | Set-Content 'ecs/service-definition.json'"
) else (
    rem Remove loadBalancers section using PowerShell
    powershell -Command "$json = Get-Content 'ecs/service-definition.json' | ConvertFrom-Json; $json.PSObject.Properties.Remove('loadBalancers'); $json.PSObject.Properties.Remove('healthCheckGracePeriodSeconds'); $json | ConvertTo-Json -Depth 10 | Set-Content 'ecs/service-definition.json'"
)

rem Check if service exists
echo Checking if service exists...
for /f "tokens=*" %%i in ('aws ecs describe-services --cluster !CLUSTER_NAME! --services crm-service --region !AWS_REGION! --query "services[?serviceName==`crm-service`].serviceName" --output text 2^>nul') do set SERVICE_EXISTS=%%i

if "!SERVICE_EXISTS!"=="" (
    echo Creating ECS service...
    aws ecs create-service --cli-input-json file://ecs/service-definition.json --region !AWS_REGION! >nul
) else (
    echo Updating existing ECS service...
    aws ecs update-service --cluster !CLUSTER_NAME! --service crm-service --task-definition "!TASK_DEF_ARN!" --region !AWS_REGION! >nul
)

echo Waiting for service to become stable...
aws ecs wait services-stable --cluster !CLUSTER_NAME! --services crm-service --region !AWS_REGION!

rem Verify deployment
echo.
echo === Deployment Status ===
aws ecs describe-services --cluster !CLUSTER_NAME! --services crm-service --region !AWS_REGION! --query "services[0].{ServiceName:serviceName,Status:status,RunningCount:runningCount,DesiredCount:desiredCount}" --output table

if /i "!NEED_LB!"=="y" (
    echo.
    echo === Load Balancer Information ===
    for /f "tokens=*" %%i in ('aws elbv2 describe-load-balancers --load-balancer-arns !ALB_ARN! --region !AWS_REGION! --query "LoadBalancers[0].DNSName" --output text') do set ALB_DNS=%%i
    echo Application URL: http://!ALB_DNS!
    echo Health Check URL: http://!ALB_DNS!/appinfo/health
)

echo.
echo CloudWatch Logs: /ecs/crm
echo === Deployment Completed Successfully ===

pause