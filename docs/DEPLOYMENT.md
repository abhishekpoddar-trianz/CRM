# CRM Application - AWS ECS Fargate Deployment Guide

## Table of Contents
1. [Prerequisites](#prerequisites)
2. [Local Development Setup](#local-development-setup)
3. [AWS ECS Fargate Prerequisites](#aws-ecs-fargate-prerequisites)
4. [Building and Pushing Docker Image](#building-and-pushing-docker-image)
5. [ECS Fargate Deployment](#ecs-fargate-deployment)
6. [Configuration Management](#configuration-management)
7. [Monitoring and Logging](#monitoring-and-logging)
8. [Troubleshooting](#troubleshooting)
9. [Scaling and Management](#scaling-and-management)
10. [Security Considerations](#security-considerations)

---

## Prerequisites

### System Requirements
- **Docker**: Version 20.10 or higher
- **Docker Compose**: Version 1.29 or higher (for local development)
- **AWS CLI**: Version 2.x
- **Java**: JDK 8 or higher (for local development)
- **Maven**: Version 3.6 or higher (for local builds)
- **Git**: For source code management

### AWS Account Requirements
- Active AWS account with appropriate permissions
- IAM user with ECS, ECR, EC2, and CloudWatch permissions
- AWS CLI configured with credentials (`aws configure`)

### Application Details
- **Technology Stack**: Spring Boot 1.5.10, Java 8
- **Build Tool**: Maven 3.x
- **Application Port**: 8080
- **Health Endpoint**: `/appinfo/health`
- **Management Context**: `/appinfo`

---

## Local Development Setup

### 1. Clone Repository
```bash
cd /path/to/project
```

### 2. Build with Maven (Local)
```bash
mvn clean package -DskipTests
```

### 3. Run with Docker Compose

**Prerequisites**: Ensure you have a MySQL database available. Update the `docker-compose.yml` environment variables accordingly.

```bash
# Build and start the application
docker-compose up --build

# Or run in detached mode
docker-compose up -d

# View logs
docker-compose logs -f crm-app

# Stop the application
docker-compose down
```

### 4. Access the Application
- **Application**: http://localhost:8080
- **Health Check**: http://localhost:8080/appinfo/health
- **Login Page**: http://localhost:8080/login

### 5. Default Users (from data.sql)
- **Admin**: admin / admin
- **Owner**: owner / owner
- **Manager**: manager / manager
- **User**: user / user

---

## AWS ECS Fargate Prerequisites

### 1. AWS Infrastructure Setup

#### Create VPC (if not exists)
```bash
aws ec2 create-vpc --cidr-block 10.0.0.0/16 --region us-east-1
```

#### Create Subnets (at least 2 for high availability)
```bash
# Public Subnet 1
aws ec2 create-subnet --vpc-id <VPC_ID> --cidr-block 10.0.1.0/24 --availability-zone us-east-1a

# Public Subnet 2
aws ec2 create-subnet --vpc-id <VPC_ID> --cidr-block 10.0.2.0/24 --availability-zone us-east-1b
```

#### Create Internet Gateway
```bash
aws ec2 create-internet-gateway
aws ec2 attach-internet-gateway --vpc-id <VPC_ID> --internet-gateway-id <IGW_ID>
```

#### Create Security Group
```bash
aws ec2 create-security-group \
  --group-name crm-app-sg \
  --description "Security group for CRM application" \
  --vpc-id <VPC_ID>

# Allow HTTP traffic
aws ec2 authorize-security-group-ingress \
  --group-id <SG_ID> \
  --protocol tcp \
  --port 80 \
  --cidr 0.0.0.0/0

# Allow application port
aws ec2 authorize-security-group-ingress \
  --group-id <SG_ID> \
  --protocol tcp \
  --port 8080 \
  --cidr 0.0.0.0/0
```

### 2. IAM Roles Setup

#### ECS Task Execution Role
This role allows ECS to pull images from ECR and send logs to CloudWatch.

```bash
# Create trust policy file (ecs-task-trust-policy.json)
cat > ecs-task-trust-policy.json <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": {
        "Service": "ecs-tasks.amazonaws.com"
      },
      "Action": "sts:AssumeRole"
    }
  ]
}
EOF

# Create role
aws iam create-role \
  --role-name ecsTaskExecutionRole \
  --assume-role-policy-document file://ecs-task-trust-policy.json

# Attach AWS managed policy
aws iam attach-role-policy \
  --role-name ecsTaskExecutionRole \
  --policy-arn arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy
```

#### ECS Task Role (Optional)
This role allows your application to access other AWS services.

```bash
# Create role
aws iam create-role \
  --role-name ecsTaskRole \
  --assume-role-policy-document file://ecs-task-trust-policy.json

# Attach policies as needed (e.g., S3, DynamoDB, etc.)
```

### 3. Database Setup

#### Option A: Amazon RDS MySQL
```bash
aws rds create-db-instance \
  --db-instance-identifier crm-database \
  --db-instance-class db.t3.micro \
  --engine mysql \
  --master-username admin \
  --master-user-password <password> \
  --allocated-storage 20 \
  --vpc-security-group-ids <SG_ID> \
  --availability-zone us-east-1a
```

#### Option B: External MySQL Server
Ensure your ECS security group can access the database on port 3306.

---

## Building and Pushing Docker Image

### Option 1: Using Build Script (Recommended)

#### Linux/macOS
```bash
chmod +x scripts/build-push.sh
./scripts/build-push.sh
```

#### Windows
```cmd
scripts\build-push.bat
```

The script will:
1. Prompt for registry selection (AWS ECR or Docker Hub)
2. Request registry credentials and details
3. Sanitize image names and tags
4. Build the Docker image
5. Authenticate with the selected registry
6. Push the image to the registry
7. Display the full image URI for deployment

### Option 2: Manual Build and Push

#### AWS ECR
```bash
# Login to ECR
aws ecr get-login-password --region us-east-1 | \
  docker login --username AWS --password-stdin <ACCOUNT_ID>.dkr.ecr.us-east-1.amazonaws.com

# Create repository (if not exists)
aws ecr create-repository --repository-name crm-app --region us-east-1

# Build image
docker build -t crm-app:latest .

# Tag image
docker tag crm-app:latest <ACCOUNT_ID>.dkr.ecr.us-east-1.amazonaws.com/crm-app:latest

# Push image
docker push <ACCOUNT_ID>.dkr.ecr.us-east-1.amazonaws.com/crm-app:latest
```

#### Docker Hub
```bash
# Login to Docker Hub
docker login -u <username>

# Build image
docker build -t <username>/crm-app:latest .

# Push image
docker push <username>/crm-app:latest
```

---

## ECS Fargate Deployment

### Understanding ECS Fargate

AWS Fargate is a serverless compute engine for containers that removes the need to manage EC2 instances. Key characteristics:

- **Serverless**: No infrastructure management
- **Network Mode**: Always uses `awsvpc` mode (each task gets its own ENI)
- **Launch Type**: Must specify `FARGATE` compatibility
- **CPU/Memory**: Must use valid Fargate combinations

### Valid Fargate CPU/Memory Combinations

| CPU (vCPU) | Memory (MB) |
|------------|-------------|
| 256 (.25)  | 512, 1024, 2048 |
| 512 (.5)   | 1024, 2048, 3072, 4096 |
| 1024 (1)   | 2048, 3072, 4096, 5120, 6144, 7168, 8192 |
| 2048 (2)   | 4096-16384 (increments of 1024) |
| 4096 (4)   | 8192-30720 (increments of 1024) |

**Default Configuration**: CPU: 512, Memory: 1024

### ECS Task Definition Explained

The task definition (`ecs/task-definition.json`) describes how to run your containerized application.

**Key Components**:

1. **Family**: Logical grouping of task definition versions (`crm-app-task`)
2. **Network Mode**: `awsvpc` (required for Fargate)
3. **Requires Compatibilities**: `["FARGATE"]`
4. **CPU and Memory**: Task-level resource allocation
5. **Execution Role**: IAM role for ECS agent (pull images, write logs)
6. **Task Role**: IAM role for application (access AWS services)
7. **Container Definitions**:
   - **Name**: Container identifier
   - **Image**: Full ECR/Docker Hub image URI
   - **Port Mappings**: Only `containerPort` (no `hostPort` in Fargate)
   - **Environment Variables**: Application configuration
   - **Health Check**: Container-level health monitoring
   - **Log Configuration**: CloudWatch Logs integration

### ECS Service Configuration

The service definition (`ecs/service-definition.json`) manages running tasks.

**Key Components**:

1. **Service Name**: Unique identifier (`crm-app-service`)
2. **Cluster**: ECS cluster where service runs
3. **Task Definition**: References task family
4. **Desired Count**: Number of tasks to run (2 for high availability)
5. **Launch Type**: `FARGATE`
6. **Network Configuration**:
   - **Subnets**: At least 2 subnets in different AZs
   - **Security Groups**: Controls inbound/outbound traffic
   - **Assign Public IP**: `ENABLED` for internet access
7. **Load Balancers** (optional): ALB/NLB integration
8. **Deployment Configuration**:
   - **Maximum Percent**: Maximum tasks during deployment (200%)
   - **Minimum Healthy Percent**: Minimum tasks during deployment (50%)
   - **Circuit Breaker**: Automatic rollback on failure

### CloudWatch Logs Setup

```bash
# Create log group
aws logs create-log-group --log-group-name /ecs/crm-app --region us-east-1

# Set retention policy (optional)
aws logs put-retention-policy \
  --log-group-name /ecs/crm-app \
  --retention-in-days 7 \
  --region us-east-1
```

### Deployment Walkthrough

#### Using Deployment Script (Recommended)

##### Linux/macOS
```bash
chmod +x scripts/deploy-image.sh
./scripts/deploy-image.sh
```

##### Windows
```cmd
scripts\deploy-image.bat
```

The script will:
1. Prompt for AWS region and ECS cluster name
2. Request network configuration (VPC, subnets, security group)
3. Ask for ECR image URI
4. Request database configuration
5. Retrieve AWS account ID automatically
6. Check/create ECS cluster
7. Create CloudWatch log group
8. Optionally create Application Load Balancer and Target Group
9. Replace placeholders in task and service definitions
10. Register task definition
11. Create or update ECS service
12. Wait for service stability
13. Display deployment status and access URLs

#### Manual Deployment

##### Step 1: Create ECS Cluster
```bash
aws ecs create-cluster --cluster-name crm-cluster --region us-east-1
```

##### Step 2: Register Task Definition

Update `ecs/task-definition.json` with your values:
- Replace `{{IMAGE_URI}}` with your image URI
- Replace `{{AWS_REGION}}` with your region
- Replace `{{ACCOUNT_ID}}` with your account ID
- Replace database placeholders with actual values

```bash
aws ecs register-task-definition \
  --cli-input-json file://ecs/task-definition.json \
  --region us-east-1
```

##### Step 3: Create ECS Service

Update `ecs/service-definition.json` with your values:
- Replace `{{CLUSTER_NAME}}` with your cluster name
- Replace `{{SUBNET_1}}` and `{{SUBNET_2}}` with subnet IDs
- Replace `{{SECURITY_GROUP}}` with security group ID
- Replace `{{TARGET_GROUP_ARN}}` with target group ARN (if using ALB)

```bash
aws ecs create-service \
  --cli-input-json file://ecs/service-definition.json \
  --region us-east-1
```

##### Step 4: Monitor Deployment
```bash
# Wait for service stability
aws ecs wait services-stable \
  --cluster crm-cluster \
  --services crm-app-service \
  --region us-east-1

# Check service status
aws ecs describe-services \
  --cluster crm-cluster \
  --services crm-app-service \
  --region us-east-1
```

---

## Configuration Management

### Environment Variables

The application uses environment variables for configuration. Key variables:

#### Application Configuration
- `SPRING_PROFILES_ACTIVE`: Spring profile (default: `production`)
- `SERVER_PORT`: Application port (default: `8080`)
- `MANAGEMENT_CONTEXT_PATH`: Actuator context path (default: `/appinfo`)

#### JVM Configuration
- `JAVA_OPTS`: JVM options (heap size, GC settings)
  - Default: `-Xmx768m -Xms256m -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0`

#### Database Configuration
- `DB_HOST`: Database host
- `DB_PORT`: Database port (default: `3306`)
- `DB_NAME`: Database name (default: `crm`)
- `DB_USERNAME`: Database username
- `DB_PASSWORD`: Database password
- `DDL_AUTO`: Hibernate DDL mode (default: `validate`)

#### System Configuration
- `TZ`: Timezone (default: `UTC`)

### Updating Configuration

#### Option 1: Update Task Definition
1. Modify environment variables in `ecs/task-definition.json`
2. Register new task definition revision
3. Update service to use new revision

```bash
aws ecs register-task-definition --cli-input-json file://ecs/task-definition.json
aws ecs update-service \
  --cluster crm-cluster \
  --service crm-app-service \
  --task-definition crm-app-task:2 \
  --force-new-deployment
```

#### Option 2: Use AWS Systems Manager Parameter Store
1. Store secrets in Parameter Store
2. Reference in task definition using `valueFrom`
3. Grant task execution role access to parameters

```json
{
  "name": "DB_PASSWORD",
  "valueFrom": "arn:aws:ssm:us-east-1:123456789:parameter/crm/db/password"
}
```

#### Option 3: Use AWS Secrets Manager
1. Store secrets in Secrets Manager
2. Reference in task definition
3. Grant task execution role access

---

## Monitoring and Logging

### CloudWatch Logs

#### View Logs
```bash
# Tail logs
aws logs tail /ecs/crm-app --follow --region us-east-1

# View specific log stream
aws logs get-log-events \
  --log-group-name /ecs/crm-app \
  --log-stream-name ecs/crm-app/<task-id> \
  --region us-east-1
```

#### CloudWatch Logs Insights
```sql
-- Query application errors
fields @timestamp, @message
| filter @message like /ERROR/
| sort @timestamp desc
| limit 100

-- Query slow requests
fields @timestamp, @message
| filter @message like /Request processing time/
| parse @message /time: (?<duration>\d+)ms/
| filter duration > 1000
| sort duration desc
```

### CloudWatch Metrics

#### ECS Service Metrics
- `CPUUtilization`: CPU usage percentage
- `MemoryUtilization`: Memory usage percentage
- `TargetGroupHealthyHostCount`: Number of healthy targets (if using ALB)
- `TargetGroupUnHealthyHostCount`: Number of unhealthy targets

#### Application Metrics (via Spring Boot Actuator)
Access metrics at: `http://<alb-dns>/appinfo/metrics`

### Health Checks

#### Container Health Check
Configured in task definition:
```json
"healthCheck": {
  "command": ["CMD-SHELL", "wget --no-verbose --tries=1 --spider http://localhost:8080/appinfo/health || exit 1"],
  "interval": 30,
  "timeout": 5,
  "retries": 3,
  "startPeriod": 90
}
```

#### ALB Health Check
Configured in target group:
- **Path**: `/appinfo/health`
- **Interval**: 30 seconds
- **Timeout**: 5 seconds
- **Healthy Threshold**: 2
- **Unhealthy Threshold**: 3

---

## Troubleshooting

### Common Issues

#### Issue 1: Task Fails to Start

**Symptoms**: Tasks transition from PENDING to STOPPED immediately

**Possible Causes**:
1. Invalid CPU/memory combination
2. Image not found in ECR/Docker Hub
3. Execution role missing permissions
4. Network configuration issues

**Solutions**:
```bash
# Check stopped task reason
aws ecs describe-tasks \
  --cluster crm-cluster \
  --tasks <task-id> \
  --region us-east-1 \
  --query 'tasks[0].stoppedReason'

# Check stopped container reason
aws ecs describe-tasks \
  --cluster crm-cluster \
  --tasks <task-id> \
  --region us-east-1 \
  --query 'tasks[0].containers[0].reason'
```

#### Issue 2: Application Not Accessible

**Symptoms**: Cannot access application via ALB DNS

**Possible Causes**:
1. Security group not allowing inbound traffic
2. Target group health checks failing
3. Incorrect port mappings

**Solutions**:
```bash
# Check target health
aws elbv2 describe-target-health \
  --target-group-arn <TG_ARN> \
  --region us-east-1

# Update security group ingress rules
aws ec2 authorize-security-group-ingress \
  --group-id <SG_ID> \
  --protocol tcp \
  --port 8080 \
  --cidr 0.0.0.0/0
```

#### Issue 3: Database Connection Failures

**Symptoms**: Application logs show database connection errors

**Possible Causes**:
1. Incorrect database credentials
2. Database security group not allowing ECS tasks
3. Network connectivity issues

**Solutions**:
```bash
# Verify environment variables in task definition
aws ecs describe-task-definition \
  --task-definition crm-app-task \
  --query 'taskDefinition.containerDefinitions[0].environment'

# Test database connectivity from ECS task
aws ecs execute-command \
  --cluster crm-cluster \
  --task <task-id> \
  --container crm-app \
  --interactive \
  --command "/bin/sh"
```

#### Issue 4: High CPU/Memory Usage

**Symptoms**: Tasks being killed due to resource constraints

**Solutions**:
1. Increase CPU/memory in task definition
2. Optimize JVM heap settings
3. Profile application for memory leaks

```json
// Update task definition
{
  "cpu": "1024",
  "memory": "2048"
}

// Update JAVA_OPTS
{
  "name": "JAVA_OPTS",
  "value": "-Xmx1536m -Xms512m -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"
}
```

#### Issue 5: Service Not Scaling

**Symptoms**: Desired count not matching running count

**Solutions**:
```bash
# Check service events
aws ecs describe-services \
  --cluster crm-cluster \
  --services crm-app-service \
  --region us-east-1 \
  --query 'services[0].events'

# Force new deployment
aws ecs update-service \
  --cluster crm-cluster \
  --service crm-app-service \
  --force-new-deployment \
  --region us-east-1
```

### Debug Commands

```bash
# List all tasks in cluster
aws ecs list-tasks --cluster crm-cluster --region us-east-1

# Describe task details
aws ecs describe-tasks --cluster crm-cluster --tasks <task-id> --region us-east-1

# View task logs
aws logs tail /ecs/crm-app --follow --region us-east-1

# Execute command in running task (requires ECS Exec enabled)
aws ecs execute-command \
  --cluster crm-cluster \
  --task <task-id> \
  --container crm-app \
  --interactive \
  --command "/bin/sh"
```

---

## Scaling and Management

### Manual Scaling

```bash
# Scale service to 3 tasks
aws ecs update-service \
  --cluster crm-cluster \
  --service crm-app-service \
  --desired-count 3 \
  --region us-east-1
```

### Auto Scaling

#### Register Scalable Target
```bash
aws application-autoscaling register-scalable-target \
  --service-namespace ecs \
  --scalable-dimension ecs:service:DesiredCount \
  --resource-id service/crm-cluster/crm-app-service \
  --min-capacity 2 \
  --max-capacity 10 \
  --region us-east-1
```

#### Create Scaling Policy (Target Tracking)
```bash
# CPU-based scaling
aws application-autoscaling put-scaling-policy \
  --service-namespace ecs \
  --scalable-dimension ecs:service:DesiredCount \
  --resource-id service/crm-cluster/crm-app-service \
  --policy-name cpu-scaling-policy \
  --policy-type TargetTrackingScaling \
  --target-tracking-scaling-policy-configuration file://cpu-scaling-policy.json \
  --region us-east-1

# cpu-scaling-policy.json
{
  "TargetValue": 70.0,
  "PredefinedMetricSpecification": {
    "PredefinedMetricType": "ECSServiceAverageCPUUtilization"
  },
  "ScaleInCooldown": 300,
  "ScaleOutCooldown": 60
}
```

### Blue/Green Deployment

#### Using AWS CodeDeploy
1. Create CodeDeploy application
2. Create deployment group with ECS configuration
3. Create appspec.yml for deployment
4. Trigger deployment via CodeDeploy

```yaml
# appspec.yml
version: 0.0
Resources:
  - TargetService:
      Type: AWS::ECS::Service
      Properties:
        TaskDefinition: "arn:aws:ecs:us-east-1:123456789:task-definition/crm-app-task:2"
        LoadBalancerInfo:
          ContainerName: "crm-app"
          ContainerPort: 8080
```

### Rolling Updates

```bash
# Update service with new task definition
aws ecs update-service \
  --cluster crm-cluster \
  --service crm-app-service \
  --task-definition crm-app-task:3 \
  --force-new-deployment \
  --region us-east-1

# Monitor deployment
aws ecs describe-services \
  --cluster crm-cluster \
  --services crm-app-service \
  --region us-east-1 \
  --query 'services[0].deployments'
```

---

## Security Considerations

### 1. Container Security

- **Non-root User**: Application runs as non-root user (`spring:spring`)
- **Minimal Base Image**: Uses Alpine Linux for smaller attack surface
- **No Secrets in Image**: All secrets passed via environment variables
- **Image Scanning**: Enable ECR image scanning

```bash
# Enable ECR image scanning
aws ecr put-image-scanning-configuration \
  --repository-name crm-app \
  --image-scanning-configuration scanOnPush=true \
  --region us-east-1
```

### 2. Network Security

- **VPC Configuration**: Deploy in private subnets for production
- **Security Groups**: Restrict inbound traffic to necessary ports only
- **NACLs**: Additional network-level security

```bash
# Example: Restrict ingress to ALB only
aws ec2 authorize-security-group-ingress \
  --group-id <ECS_SG_ID> \
  --protocol tcp \
  --port 8080 \
  --source-group <ALB_SG_ID>
```

### 3. Secrets Management

**Option A: AWS Secrets Manager**
```bash
# Store database password
aws secretsmanager create-secret \
  --name crm/db/password \
  --secret-string "<password>" \
  --region us-east-1

# Reference in task definition
{
  "name": "DB_PASSWORD",
  "valueFrom": "arn:aws:secretsmanager:us-east-1:123456789:secret:crm/db/password"
}

# Grant task execution role access
{
  "Effect": "Allow",
  "Action": [
    "secretsmanager:GetSecretValue"
  ],
  "Resource": "arn:aws:secretsmanager:us-east-1:123456789:secret:crm/db/password*"
}
```

**Option B: AWS Systems Manager Parameter Store**
```bash
# Store database password (encrypted)
aws ssm put-parameter \
  --name /crm/db/password \
  --value "<password>" \
  --type SecureString \
  --region us-east-1

# Reference in task definition
{
  "name": "DB_PASSWORD",
  "valueFrom": "arn:aws:ssm:us-east-1:123456789:parameter/crm/db/password"
}
```

### 4. IAM Permissions

**Principle of Least Privilege**: Grant only necessary permissions.

**Task Execution Role** (for ECS agent):
- `ecr:GetAuthorizationToken`
- `ecr:BatchCheckLayerAvailability`
- `ecr:GetDownloadUrlForLayer`
- `ecr:BatchGetImage`
- `logs:CreateLogStream`
- `logs:PutLogEvents`
- `secretsmanager:GetSecretValue` (if using Secrets Manager)
- `ssm:GetParameters` (if using Parameter Store)

**Task Role** (for application):
- Grant permissions based on application needs (S3, DynamoDB, etc.)

### 5. Compliance and Auditing

- **CloudTrail**: Enable for ECS API logging
- **Config**: Monitor ECS resource compliance
- **GuardDuty**: Threat detection for ECS workloads

### 6. Data Encryption

- **In Transit**: Use TLS/SSL for all communications
- **At Rest**: Encrypt EBS volumes, RDS databases, S3 buckets
- **Application Level**: Implement application-level encryption for sensitive data

---

## Additional Resources

### AWS Documentation
- [Amazon ECS Developer Guide](https://docs.aws.amazon.com/ecs/)
- [AWS Fargate Documentation](https://docs.aws.amazon.com/AmazonECS/latest/developerguide/AWS_Fargate.html)
- [Amazon ECR User Guide](https://docs.aws.amazon.com/ecr/)
- [CloudWatch Logs Documentation](https://docs.aws.amazon.com/cloudwatch/)

### Spring Boot Resources
- [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/1.5.10.RELEASE/reference/html/)
- [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/1.5.10.RELEASE/reference/html/production-ready.html)

### Best Practices
- [ECS Best Practices Guide](https://docs.aws.amazon.com/AmazonECS/latest/bestpracticesguide/intro.html)
- [Docker Best Practices](https://docs.docker.com/develop/dev-best-practices/)
- [Java Performance Tuning](https://docs.oracle.com/javase/8/docs/technotes/guides/vm/performance-enhancements-7.html)

---

## Support and Maintenance

### Regular Maintenance Tasks

1. **Update Dependencies**: Regularly update Spring Boot and dependencies
2. **Security Patches**: Monitor and apply security updates
3. **Image Updates**: Rebuild images with latest base image versions
4. **Log Rotation**: Configure CloudWatch Logs retention policies
5. **Cost Optimization**: Monitor and optimize resource usage

### Monitoring Checklist

- [ ] CPU and memory utilization within acceptable ranges
- [ ] No unhealthy targets in target group
- [ ] Application logs show no critical errors
- [ ] Database connection pool healthy
- [ ] Response times within SLA
- [ ] No failed deployments
- [ ] Auto Scaling policies functioning correctly

### Backup and Disaster Recovery

1. **Database Backups**: Configure RDS automated backups
2. **Configuration Backups**: Version control all configuration files
3. **Multi-AZ Deployment**: Deploy across multiple availability zones
4. **Disaster Recovery Plan**: Document recovery procedures

---

## Conclusion

This deployment guide provides comprehensive instructions for deploying the CRM application to AWS ECS Fargate. For additional assistance or questions, consult the AWS documentation or contact your DevOps team.

**Important Notes**:
- Always test deployments in non-production environments first
- Monitor costs and set up billing alerts
- Implement proper security measures before exposing to the internet
- Keep all dependencies and frameworks up to date
- Document any customizations or deviations from this guide

---

**Version**: 1.0  
**Last Updated**: 2026-01-22  
**Target Platform**: AWS ECS Fargate
