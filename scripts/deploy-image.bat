@echo off
setlocal enabledelayedexpansion

echo ============================================
echo   CRM Application - ECS Fargate Deployment
echo ============================================
echo.

REM Configuration
set SERVICE_NAME=crm-app-service
set TASK_FAMILY=crm-app-task
set CONTAINER_NAME=crm-app
set APP_PORT=8080

REM Prompt for AWS configuration
set /p AWS_REGION="Enter AWS Region [us-east-1]: "
if "!AWS_REGION!"=="" set AWS_REGION=us-east-1

set /p CLUSTER_NAME="Enter ECS Cluster Name [crm-cluster]: "
if "!CLUSTER_NAME!"=="" set CLUSTER_NAME=crm-cluster

echo.
echo --- Network Configuration ---
set /p VPC_ID="Enter VPC ID: "
if "!VPC_ID!"=="" (
    echo Error: VPC ID is required
    exit /b 1
)

set /p SUBNETS_INPUT="Enter Subnet IDs (comma-separated, at least 2): "
if "!SUBNETS_INPUT!"=="" (
    echo Error: At least 2 subnet IDs are required
    exit /b 1
)

REM Parse subnets
for /f "tokens=1,2 delims=," %%a in ("!SUBNETS_INPUT!") do (
    set SUBNET_1=%%a
    set SUBNET_2=%%b
)
set SUBNET_1=!SUBNET_1: =!
set SUBNET_2=!SUBNET_2: =!
if "!SUBNET_2!"=="" set SUBNET_2=!SUBNET_1!

set /p SECURITY_GROUP="Enter Security Group ID: "
if "!SECURITY_GROUP!"=="" (
    echo Error: Security Group ID is required
    exit /b 1
)

echo.
echo --- Container Image ---
set /p IMAGE_URI="Enter ECR Image URI: "
if "!IMAGE_URI!"=="" (
    echo Error: Image URI is required
    exit /b 1
)

echo.
echo --- Database Configuration ---
set /p DB_HOST="Enter Database Host: "
if "!DB_HOST!"=="" set DB_HOST=localhost

set /p DB_PORT="Enter Database Port [3306]: "
if "!DB_PORT!"=="" set DB_PORT=3306

set /p DB_NAME="Enter Database Name [crm]: "
if "!DB_NAME!"=="" set DB_NAME=crm

set /p DB_USERNAME="Enter Database Username: "
if "!DB_USERNAME!"=="" (
    echo Error: Database username is required
    exit /b 1
)

set /p DB_PASSWORD="Enter Database Password: "
if "!DB_PASSWORD!"=="" (
    echo Error: Database password is required
    exit /b 1
)

REM Get AWS Account ID
echo.
echo Retrieving AWS Account ID...
for /f "delims=" %%i in ('aws sts get-caller-identity --query Account --output text') do set ACCOUNT_ID=%%i
echo AWS Account ID: !ACCOUNT_ID!

REM Check/Create ECS Cluster
echo.
echo Checking ECS cluster...
aws ecs describe-clusters --clusters !CLUSTER_NAME! --region !AWS_REGION! >nul 2>&1
if !ERRORLEVEL! neq 0 (
    echo Cluster does not exist. Creating ECS cluster: !CLUSTER_NAME!
    aws ecs create-cluster --cluster-name !CLUSTER_NAME! --region !AWS_REGION!
    echo Cluster created successfully
)

REM Create CloudWatch Log Group
echo.
echo Creating CloudWatch log group...
aws logs create-log-group --log-group-name "/ecs/crm-app" --region !AWS_REGION! 2>nul
if !ERRORLEVEL! neq 0 (
    echo Log group already exists
)

REM Load Balancer Configuration
echo.
set /p NEED_LB="Do you need a load balancer for this service? (y/n) [y]: "
if "!NEED_LB!"=="" set NEED_LB=y

if /i "!NEED_LB!"=="y" (
    echo Creating Application Load Balancer and Target Group...
    
    set ALB_NAME=crm-app-alb
    echo Creating ALB: !ALB_NAME!
    
    for /f "delims=" %%i in ('aws elbv2 create-load-balancer --name !ALB_NAME! --subnets !SUBNET_1! !SUBNET_2! --security-groups !SECURITY_GROUP! --scheme internet-facing --type application --ip-address-type ipv4 --region !AWS_REGION! --query LoadBalancers[0].LoadBalancerArn --output text 2^>nul') do set ALB_ARN=%%i
    
    if "!ALB_ARN!"=="" (
        for /f "delims=" %%i in ('aws elbv2 describe-load-balancers --names !ALB_NAME! --region !AWS_REGION! --query LoadBalancers[0].LoadBalancerArn --output text') do set ALB_ARN=%%i
    )
    
    echo ALB ARN: !ALB_ARN!
    
    for /f "delims=" %%i in ('aws elbv2 describe-load-balancers --load-balancer-arns !ALB_ARN! --region !AWS_REGION! --query LoadBalancers[0].DNSName --output text') do set ALB_DNS=%%i
    
    set TG_NAME=crm-app-tg
    echo Creating Target Group: !TG_NAME!
    
    for /f "delims=" %%i in ('aws elbv2 create-target-group --name !TG_NAME! --protocol HTTP --port !APP_PORT! --vpc-id !VPC_ID! --target-type ip --health-check-enabled --health-check-protocol HTTP --health-check-path "/appinfo/health" --health-check-interval-seconds 30 --health-check-timeout-seconds 5 --healthy-threshold-count 2 --unhealthy-threshold-count 3 --region !AWS_REGION! --query TargetGroups[0].TargetGroupArn --output text 2^>nul') do set TARGET_GROUP_ARN=%%i
    
    if "!TARGET_GROUP_ARN!"=="" (
        for /f "delims=" %%i in ('aws elbv2 describe-target-groups --names !TG_NAME! --region !AWS_REGION! --query TargetGroups[0].TargetGroupArn --output text') do set TARGET_GROUP_ARN=%%i
    )
    
    echo Target Group ARN: !TARGET_GROUP_ARN!
    
    echo Creating ALB Listener...
    aws elbv2 create-listener --load-balancer-arn !ALB_ARN! --protocol HTTP --port 80 --default-actions Type=forward,TargetGroupArn=!TARGET_GROUP_ARN! --region !AWS_REGION! >nul 2>&1
    
    set LB_CONFIG=yes
) else (
    echo Skipping load balancer configuration
    set TARGET_GROUP_ARN=
    set LB_CONFIG=no
)

REM Prepare task definition
echo.
echo Preparing ECS task definition...
copy ecs\task-definition.json %TEMP%\task-definition.json >nul

powershell -Command "(Get-Content '%TEMP%\task-definition.json') -replace '{{IMAGE_URI}}', '%IMAGE_URI%' | Set-Content '%TEMP%\task-definition.json'"
powershell -Command "(Get-Content '%TEMP%\task-definition.json') -replace '{{AWS_REGION}}', '%AWS_REGION%' | Set-Content '%TEMP%\task-definition.json'"
powershell -Command "(Get-Content '%TEMP%\task-definition.json') -replace '{{ACCOUNT_ID}}', '%ACCOUNT_ID%' | Set-Content '%TEMP%\task-definition.json'"
powershell -Command "(Get-Content '%TEMP%\task-definition.json') -replace '{{DB_HOST}}', '%DB_HOST%' | Set-Content '%TEMP%\task-definition.json'"
powershell -Command "(Get-Content '%TEMP%\task-definition.json') -replace '{{DB_PORT}}', '%DB_PORT%' | Set-Content '%TEMP%\task-definition.json'"
powershell -Command "(Get-Content '%TEMP%\task-definition.json') -replace '{{DB_NAME}}', '%DB_NAME%' | Set-Content '%TEMP%\task-definition.json'"
powershell -Command "(Get-Content '%TEMP%\task-definition.json') -replace '{{DB_USERNAME}}', '%DB_USERNAME%' | Set-Content '%TEMP%\task-definition.json'"
powershell -Command "(Get-Content '%TEMP%\task-definition.json') -replace '{{DB_PASSWORD}}', '%DB_PASSWORD%' | Set-Content '%TEMP%\task-definition.json'"

REM Register task definition
echo Registering task definition...
for /f "delims=" %%i in ('aws ecs register-task-definition --cli-input-json file://%TEMP%\task-definition.json --region !AWS_REGION! --query taskDefinition.taskDefinitionArn --output text') do set TASK_DEF_ARN=%%i

echo Task Definition ARN: !TASK_DEF_ARN!

REM Prepare service definition
echo.
echo Preparing ECS service definition...
copy ecs\service-definition.json %TEMP%\service-definition.json >nul

powershell -Command "(Get-Content '%TEMP%\service-definition.json') -replace '{{CLUSTER_NAME}}', '%CLUSTER_NAME%' | Set-Content '%TEMP%\service-definition.json'"
powershell -Command "(Get-Content '%TEMP%\service-definition.json') -replace '{{SUBNET_1}}', '%SUBNET_1%' | Set-Content '%TEMP%\service-definition.json'"
powershell -Command "(Get-Content '%TEMP%\service-definition.json') -replace '{{SUBNET_2}}', '%SUBNET_2%' | Set-Content '%TEMP%\service-definition.json'"
powershell -Command "(Get-Content '%TEMP%\service-definition.json') -replace '{{SECURITY_GROUP}}', '%SECURITY_GROUP%' | Set-Content '%TEMP%\service-definition.json'"

if "!LB_CONFIG!"=="yes" (
    powershell -Command "(Get-Content '%TEMP%\service-definition.json') -replace '{{TARGET_GROUP_ARN}}', '%TARGET_GROUP_ARN%' | Set-Content '%TEMP%\service-definition.json'"
) else (
    powershell -Command "$content = Get-Content '%TEMP%\service-definition.json' | Out-String; $content = $content -replace '\s*\"loadBalancers\"[\s\S]*?\],', ''; $content = $content -replace '\s*\"healthCheckGracePeriodSeconds\"[^,]*,', ''; $content | Set-Content '%TEMP%\service-definition.json'"
)

REM Check if service exists
echo.
echo Checking if service exists...
for /f "delims=" %%i in ('aws ecs describe-services --cluster !CLUSTER_NAME! --services !SERVICE_NAME! --region !AWS_REGION! --query services[0].serviceName --output text 2^>nul') do set EXISTING_SERVICE=%%i

if "!EXISTING_SERVICE!"=="!SERVICE_NAME!" (
    echo Service exists. Updating service...
    aws ecs update-service --cluster !CLUSTER_NAME! --service !SERVICE_NAME! --task-definition !TASK_DEF_ARN! --force-new-deployment --region !AWS_REGION! >nul
    echo Service updated successfully
) else (
    echo Service does not exist. Creating new service...
    aws ecs create-service --cli-input-json file://%TEMP%\service-definition.json --region !AWS_REGION! >nul
    echo Service created successfully
)

REM Wait for service stability
echo.
echo Waiting for service to become stable (this may take a few minutes)...
aws ecs wait services-stable --cluster !CLUSTER_NAME! --services !SERVICE_NAME! --region !AWS_REGION!

REM Verify deployment
echo.
echo ============================================
echo Deployment completed successfully!
echo ============================================
echo.
echo Service Details:
aws ecs describe-services --cluster !CLUSTER_NAME! --services !SERVICE_NAME! --region !AWS_REGION! --query services[0].[serviceName,status,runningCount,desiredCount] --output table

if "!LB_CONFIG!"=="yes" (
    echo.
    echo Application URL: http://!ALB_DNS!
    echo Health Check: http://!ALB_DNS!/appinfo/health
)

echo.
echo CloudWatch Logs: /ecs/crm-app
echo Region: !AWS_REGION!
echo.
echo To view logs, run:
echo aws logs tail /ecs/crm-app --follow --region !AWS_REGION!
echo.

REM Cleanup temp files
del /q %TEMP%\task-definition.json %TEMP%\service-definition.json 2>nul

echo Deployment complete!
echo.

endlocal