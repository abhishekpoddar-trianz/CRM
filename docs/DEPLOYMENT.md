# CRM Application - AWS ECS Fargate Deployment Guide

This guide provides comprehensive instructions for deploying the CRM Spring Boot application to AWS ECS Fargate.

## Table of Contents

1. [Prerequisites](#prerequisites)
2. [Local Development Setup](#local-development-setup)
3. [Docker Deployment](#docker-deployment)
4. [AWS ECS Fargate Prerequisites](#aws-ecs-fargate-prerequisites)
5. [ECS Fargate Setup](#ecs-fargate-setup)
6. [Deployment Walkthrough](#deployment-walkthrough)
7. [Configuration Management](#configuration-management)
8. [Troubleshooting](#troubleshooting)
9. [Scaling and Management](#scaling-and-management)
10. [Security Considerations](#security-considerations)

## Prerequisites

### System Requirements

- Java 8 or higher
- Maven 3.6+
- Docker 20.10+
- AWS CLI v2
- Git

### Application Details

- **Framework**: Spring Boot 1.5.10
- **Java Version**: 8
- **Build Tool**: Maven
- **Package Type**: JAR
- **Default Port**: 8080
- **Health Endpoint**: `/appinfo/health`
- **Management Context**: `/appinfo`

## Local Development Setup

### 1. Clone and Build

```bash
git clone <repository-url>
cd CRM
mvn clean package
```

### 2. Run Locally

```bash
java -jar target/crm-0.0.1-SNAPSHOT.jar
```

### 3. Access Application

- Application: http://localhost:8080
- Health Check: http://localhost:8080/appinfo/health
- Management Info: http://localhost:8080/appinfo

## Docker Deployment

### 1. Build Docker Image

```bash
docker build -t crm:latest .
```

### 2. Run with Docker Compose

```bash
# Set environment variables
export DB_HOST=your-db-host
export DB_USERNAME=your-db-user
export DB_PASSWORD=your-db-password

# Start the application
docker-compose up -d
```

### 3. Verify Deployment

```bash
curl http://localhost:8080/appinfo/health
```

## AWS ECS Fargate Prerequisites

### 1. AWS CLI Configuration

```bash
aws configure
# Enter your AWS Access Key ID, Secret Access Key, Region, and Output format
```

### 2. Required AWS Resources

#### VPC and Networking
- VPC with public/private subnets
- Internet Gateway (for public subnets)
- Security Groups allowing:
  - Inbound: Port 8080 (from Load Balancer)
  - Outbound: All traffic (for database and internet access)

#### IAM Roles

**ECS Task Execution Role** (required):
```json
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
```

Attached policies:
- `AmazonECSTaskExecutionRolePolicy`
- Custom policy for ECR access if using private repositories

**ECS Task Role** (optional, for application AWS API access):
```json
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
```

### 3. Database Setup

- RDS MySQL instance or Aurora MySQL cluster
- Security group allowing inbound connections on port 3306 from ECS tasks
- Database created with name `crm`
- Database user with appropriate permissions

## ECS Fargate Setup

### 1. CloudWatch Log Group

```bash
aws logs create-log-group --log-group-name "/ecs/crm" --region us-east-1
```

### 2. ECS Task Definition Explained

The task definition (`ecs/task-definition.json`) defines:

- **CPU/Memory**: 512 CPU units (0.5 vCPU), 1024 MB memory
- **Network Mode**: `awsvpc` (required for Fargate)
- **Container Configuration**:
  - Port mapping: 8080
  - Environment variables for database connection
  - CloudWatch logging
  - JVM optimization settings

### 3. ECS Service Configuration

The service definition (`ecs/service-definition.json`) defines:

- **Launch Type**: Fargate
- **Desired Count**: 2 (for high availability)
- **Network Configuration**: VPC, subnets, security groups
- **Deployment Configuration**: Rolling updates with 50% minimum healthy
- **Load Balancer Integration**: Target group attachment

## Deployment Walkthrough

### Step 1: Build and Push Image

#### Using the Build Script (Recommended)

```bash
# Linux/macOS
./scripts/build-push.sh

# Windows
scripts\build-push.bat
```

The script will:
1. Prompt for registry selection (AWS ECR or Docker Hub)
2. Handle authentication
3. Build and tag the image
4. Push to the selected registry

#### Manual ECR Push

```bash
# Get login token
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin <account-id>.dkr.ecr.us-east-1.amazonaws.com

# Create repository (if needed)
aws ecr create-repository --repository-name crm --region us-east-1

# Build and tag
docker build -t crm:latest .
docker tag crm:latest <account-id>.dkr.ecr.us-east-1.amazonaws.com/crm:latest

# Push
docker push <account-id>.dkr.ecr.us-east-1.amazonaws.com/crm:latest
```

### Step 2: Deploy to ECS

#### Using the Deployment Script (Recommended)

```bash
# Linux/macOS
./scripts/deploy-image.sh

# Windows
scripts\deploy-image.bat
```

The script will:
1. Prompt for AWS region and cluster name
2. Collect network configuration (VPC, subnets, security groups)
3. Get database connection details
4. Optionally create Application Load Balancer
5. Register task definition
6. Create or update ECS service
7. Wait for deployment to complete

#### Manual Deployment

```bash
# Register task definition
aws ecs register-task-definition --cli-input-json file://ecs/task-definition.json

# Create service
aws ecs create-service --cli-input-json file://ecs/service-definition.json

# Wait for stability
aws ecs wait services-stable --cluster crm-cluster --services crm-service
```

### Step 3: Verify Deployment

```bash
# Check service status
aws ecs describe-services --cluster crm-cluster --services crm-service

# Check running tasks
aws ecs list-tasks --cluster crm-cluster --service-name crm-service

# View logs
aws logs get-log-events --log-group-name "/ecs/crm" --log-stream-name "ecs/crm/<task-id>"
```

## Configuration Management

### Environment Variables

The application uses the following environment variables:

| Variable | Description | Default |
|----------|-------------|----------|
| `DB_HOST` | Database host | localhost |
| `DB_PORT` | Database port | 3306 |
| `DB_NAME` | Database name | crm |
| `DB_USERNAME` | Database username | root |
| `DB_PASSWORD` | Database password | password |
| `JAVA_OPTS` | JVM options | See task definition |
| `SPRING_PROFILES_ACTIVE` | Spring profiles | docker |

### Spring Profiles

Create profile-specific configuration files:

- `application-docker.properties` - Docker/container configuration
- `application-production.properties` - Production-specific settings

Example `application-docker.properties`:
```properties
spring.datasource.url=jdbc:mysql://${DB_HOST:localhost}:${DB_PORT:3306}/${DB_NAME:crm}?useSSL=true&requireSSL=false
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=validate
logging.level.org.springframework.security=INFO
logging.level.crm=INFO
```

### Secrets Management

For production deployments, consider using AWS Secrets Manager:

1. Store database credentials in Secrets Manager
2. Update task definition to reference secrets:

```json
"secrets": [
  {
    "name": "DB_PASSWORD",
    "valueFrom": "arn:aws:secretsmanager:region:account:secret:crm/db-password"
  }
]
```

## Troubleshooting

### Common Issues

#### 1. Task Fails to Start

**Symptoms**: Tasks start but immediately stop

**Diagnosis**:
```bash
# Check service events
aws ecs describe-services --cluster crm-cluster --services crm-service

# Check task details
aws ecs describe-tasks --cluster crm-cluster --tasks <task-arn>
```

**Common Causes**:
- Invalid CPU/memory combination
- Image not found or pull errors
- Application startup failures

#### 2. Health Check Failures

**Symptoms**: Tasks fail health checks and are replaced

**Diagnosis**:
```bash
# Check application logs
aws logs get-log-events --log-group-name "/ecs/crm" --log-stream-name "ecs/crm/<task-id>"

# Test health endpoint directly
curl http://<task-ip>:8080/appinfo/health
```

**Solutions**:
- Verify health endpoint is accessible
- Check application startup time
- Verify database connectivity

#### 3. Database Connection Issues

**Symptoms**: Application logs show database connection errors

**Solutions**:
- Verify security group allows connection from ECS tasks to RDS
- Check database endpoint and credentials
- Ensure database is in the same VPC or properly configured for cross-VPC access

#### 4. Load Balancer Issues

**Symptoms**: 502/503 errors from load balancer

**Diagnosis**:
```bash
# Check target group health
aws elbv2 describe-target-health --target-group-arn <target-group-arn>
```

**Solutions**:
- Verify security groups allow traffic from load balancer to tasks
- Check health check configuration
- Ensure target group uses `ip` target type for Fargate

### Monitoring and Logging

#### CloudWatch Metrics

Monitor these key metrics:
- CPU and memory utilization
- Service running/desired count
- Target group healthy host count

#### Application Logs

Logs are automatically sent to CloudWatch Logs:
- Log Group: `/ecs/crm`
- Log Stream: `ecs/crm/<task-id>`

#### Custom Dashboards

Create CloudWatch dashboards to monitor:
- Application performance metrics
- Database connection pool status
- HTTP response times and error rates

## Scaling and Management

### Auto Scaling

#### Service Auto Scaling

```bash
# Register scalable target
aws application-autoscaling register-scalable-target \
  --service-namespace ecs \
  --resource-id service/crm-cluster/crm-service \
  --scalable-dimension ecs:service:DesiredCount \
  --min-capacity 2 \
  --max-capacity 10

# Create scaling policy
aws application-autoscaling put-scaling-policy \
  --service-namespace ecs \
  --resource-id service/crm-cluster/crm-service \
  --scalable-dimension ecs:service:DesiredCount \
  --policy-name crm-cpu-scaling \
  --policy-type TargetTrackingScaling \
  --target-tracking-scaling-policy-configuration '{
    "TargetValue": 70.0,
    "PredefinedMetricSpecification": {
      "PredefinedMetricType": "ECSServiceAverageCPUUtilization"
    }
  }'
```

### Blue/Green Deployments

For zero-downtime deployments:

1. Create new task definition revision
2. Update service with new task definition
3. ECS will automatically perform rolling deployment
4. Monitor deployment status

### Rollback Procedures

```bash
# Rollback to previous task definition
aws ecs update-service \
  --cluster crm-cluster \
  --service crm-service \
  --task-definition crm-task:1  # Previous revision
```

## Security Considerations

### Network Security

- Use private subnets for ECS tasks when possible
- Implement least-privilege security group rules
- Enable VPC Flow Logs for network monitoring

### Application Security

- Use non-root user in Docker container
- Keep base images updated
- Implement proper input validation
- Use HTTPS for external communications

### Data Security

- Encrypt data in transit and at rest
- Use AWS Secrets Manager for sensitive configuration
- Implement proper database access controls
- Enable CloudTrail for audit logging

### Container Security

- Scan images for vulnerabilities
- Use distroless or minimal base images
- Implement resource limits
- Monitor for runtime security events

## Technology-Specific Notes

### Spring Boot Considerations

- **Actuator Security**: Management endpoints are configured without security (`management.security.enabled=false`). Consider enabling security for production.
- **Profile Management**: Use Spring profiles to manage environment-specific configurations.
- **Graceful Shutdown**: Spring Boot handles SIGTERM signals for graceful shutdown.

### JVM Optimizations

- **Container Awareness**: JVM flags include `+UseContainerSupport` for proper resource detection
- **Memory Management**: MaxRAMPercentage set to 75% to leave memory for OS and other processes
- **Garbage Collection**: Consider G1GC for better performance with larger heaps

### Database Considerations

- **Connection Pooling**: HikariCP is default in Spring Boot 2.x, but 1.5.x uses Tomcat pool
- **DDL Management**: Currently set to `create-drop` for development; use `validate` or Flyway for production
- **SSL Configuration**: Database URL includes SSL configuration parameters

### Monitoring Integration

- **Spring Boot Actuator**: Provides health, info, and metrics endpoints
- **Micrometer**: Consider adding for enhanced metrics collection
- **APM Tools**: Integrate with tools like AWS X-Ray for distributed tracing

This deployment guide provides a comprehensive approach to deploying the CRM application on AWS ECS Fargate with proper security, monitoring, and scaling considerations.