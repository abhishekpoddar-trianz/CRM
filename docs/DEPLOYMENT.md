# CRM Application - AWS ECS Fargate Deployment Guide

## Overview

This guide covers the deployment of the CRM (Customer Relationship Management) application to AWS ECS Fargate. The application is built with Spring Boot 1.5.10, Java 8, and uses MySQL as the database.

## Prerequisites

### System Requirements
- Docker installed and running
- AWS CLI v2 installed and configured
- AWS account with appropriate permissions
- Java 8 JDK (for local development)
- Maven 3.6+ (for local builds)

### AWS Infrastructure Requirements

#### IAM Roles
1. **ECS Task Execution Role** (`ecsTaskExecutionRole`)
   - Allows ECS to pull container images from ECR
   - Allows ECS to write logs to CloudWatch
   
2. **ECS Task Role** (`ecsTaskRole`)
   - Provides permissions for the application to access AWS services
   - Database access permissions (if using RDS)

#### Network Infrastructure
- **VPC**: Virtual Private Cloud with public and private subnets
- **Subnets**: At least 2 subnets in different AZs for high availability
- **Security Groups**: 
  - Allow inbound traffic on port 8080 (application)
  - Allow outbound traffic to database (port 3306)
  - Allow outbound HTTPS (port 443) for ECR/internet access
- **Internet Gateway**: For public subnet access
- **NAT Gateway**: For private subnet outbound access (if using private subnets)

#### Database
- **RDS MySQL**: Managed MySQL database instance
- **Security Group**: Allow inbound traffic on port 3306 from ECS security group
- **Subnet Group**: Database subnet group for RDS placement

## Local Development Setup

### 1. Clone and Build
```bash
git clone <repository-url>
cd CRM
mvn clean package -DskipTests
```

### 2. Local Docker Development
```bash
# Build the Docker image
docker build -t crm-app:latest .

# Run with Docker Compose (requires external database)
docker-compose up
```

### 3. Environment Variables
Configure these environment variables for local development:
```bash
export DB_HOST=localhost
export DB_PORT=3306
export DB_NAME=crm
export DB_USERNAME=your_username
export DB_PASSWORD=your_password
```

## AWS ECS Fargate Deployment

### Step 1: Build and Push Docker Image

#### Option A: Using the Build Script (Recommended)
```bash
# Linux/macOS
chmod +x scripts/build-push.sh
./scripts/build-push.sh

# Windows
scripts/build-push.bat
```

#### Option B: Manual Build and Push
```bash
# Configure AWS CLI
aws configure

# Build image
docker build -t crm-app:latest .

# Tag for ECR
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin <account-id>.dkr.ecr.us-east-1.amazonaws.com
docker tag crm-app:latest <account-id>.dkr.ecr.us-east-1.amazonaws.com/crm:latest
docker push <account-id>.dkr.ecr.us-east-1.amazonaws.com/crm:latest
```

### Step 2: Deploy to ECS Fargate

#### Using the Deployment Script (Recommended)
```bash
# Linux/macOS
chmod +x scripts/deploy-image.sh
./scripts/deploy-image.sh

# Windows
scripts/deploy-image.bat
```

The script will prompt for:
- AWS region
- ECS cluster name
- VPC ID
- Subnet IDs (comma-separated)
- Security group ID
- ECR image URI
- Database connection details
- Load balancer configuration

### Step 3: Verify Deployment

1. **Check ECS Service Status**
```bash
aws ecs describe-services --cluster <cluster-name> --services crm-service
```

2. **View Application Logs**
```bash
aws logs get-log-events --log-group-name "/ecs/crm" --log-stream-name <stream-name>
```

3. **Test Application**
- If using load balancer: Access via ALB DNS name
- Health check endpoint: `/appinfo/health`
- Application endpoints: `/`, `/login`, etc.

## ECS Task Definition Configuration

### CPU and Memory
- **CPU**: 512 (.5 vCPU)
- **Memory**: 1024 MB
- **Compatible combinations**: See [AWS Fargate Task Size Documentation](https://docs.aws.amazon.com/AmazonECS/latest/developerguide/AWS_Fargate.html)

### Container Configuration
- **Port**: 8080 (application port)
- **Health Check**: `/appinfo/health` (Spring Boot Actuator with custom management context path)
- **Environment Variables**:
  - `JAVA_OPTS`: JVM memory settings
  - `SPRING_PROFILES_ACTIVE`: Application profile
  - `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME`, `DB_PASSWORD`: Database configuration

### Logging
- **Log Driver**: `awslogs`
- **Log Group**: `/ecs/crm`
- **Log Stream Prefix**: `ecs`

## ECS Service Configuration

### Fargate Launch Type
- **Network Mode**: `awsvpc` (required for Fargate)
- **Desired Count**: 2 (for high availability)
- **Deployment Configuration**:
  - Maximum Percent: 200%
  - Minimum Healthy Percent: 50%

### Load Balancer Integration
- **Type**: Application Load Balancer (ALB)
- **Target Type**: IP (required for awsvpc mode)
- **Health Check**: `/appinfo/health`
- **Health Check Grace Period**: 300 seconds (allows time for Spring Boot startup)

## Scaling and Management

### Auto Scaling
```bash
# Create auto scaling target
aws application-autoscaling register-scalable-target \
    --service-namespace ecs \
    --scalable-dimension ecs:service:DesiredCount \
    --resource-id service/<cluster-name>/crm-service \
    --min-capacity 2 \
    --max-capacity 10

# Create scaling policy
aws application-autoscaling put-scaling-policy \
    --policy-name crm-cpu-scaling \
    --service-namespace ecs \
    --resource-id service/<cluster-name>/crm-service \
    --scalable-dimension ecs:service:DesiredCount \
    --policy-type TargetTrackingScaling \
    --target-tracking-scaling-policy-configuration file://scaling-policy.json
```

### Blue/Green Deployments
- Use CodeDeploy with ECS for blue/green deployments
- Configure deployment configuration for gradual traffic shifting
- Set up CloudWatch alarms for rollback triggers

## Troubleshooting

### Common Issues

#### 1. Task Fails to Start
- Check CloudWatch logs: `/ecs/crm`
- Verify IAM roles have correct permissions
- Ensure security groups allow required traffic
- Check resource allocation (CPU/memory limits)

#### 2. Database Connection Issues
- Verify database security group allows traffic from ECS security group
- Check database endpoint and credentials
- Ensure database is in the same VPC or properly configured for cross-VPC access
- Test database connectivity from ECS tasks

#### 3. Load Balancer Health Check Failures
- Verify health check endpoint returns 200 OK
- Check health check path: `/appinfo/health`
- Adjust health check settings (interval, timeout, thresholds)
- Ensure security groups allow ALB to reach ECS tasks on port 8080

#### 4. Java Application Issues
- **Memory Issues**: Adjust `JAVA_OPTS` environment variable
- **Startup Timeout**: Increase health check grace period
- **Configuration Issues**: Verify environment variables are correctly set

### Monitoring and Logging

#### CloudWatch Metrics
- ECS Service metrics: CPU utilization, memory utilization, task count
- ALB metrics: Request count, response time, error rate
- Custom application metrics (if configured with Micrometer)

#### Application Logs
```bash
# View recent logs
aws logs tail /ecs/crm --follow

# Search logs
aws logs filter-log-events --log-group-name "/ecs/crm" --filter-pattern "ERROR"
```

### Security Considerations

1. **Network Security**
   - Use private subnets for ECS tasks when possible
   - Restrict security group rules to minimum required access
   - Enable VPC Flow Logs for network monitoring

2. **Container Security**
   - Use non-root user in container (already configured)
   - Regularly update base images for security patches
   - Scan container images for vulnerabilities

3. **Database Security**
   - Use RDS encryption at rest and in transit
   - Rotate database credentials regularly
   - Use AWS Secrets Manager for credential management

4. **IAM Security**
   - Follow principle of least privilege for IAM roles
   - Use task roles for application-specific permissions
   - Enable CloudTrail for API call logging

## Performance Optimization

### JVM Tuning
Optimize JVM settings for containerized environments:
```bash
JAVA_OPTS="-Xmx512m -Xms256m -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:+UseG1GC -XX:G1HeapRegionSize=16m -XX:+UseStringDeduplication"
```

### Spring Boot Optimizations
1. **Profile Configuration**: Use production profile with optimized settings
2. **Connection Pooling**: Configure database connection pool size
3. **Caching**: Enable Spring caching where appropriate
4. **Actuator**: Disable unnecessary actuator endpoints in production

### Database Optimization
1. **Connection Pool**: Configure HikariCP settings
2. **Query Optimization**: Use proper indexes and query optimization
3. **Read Replicas**: Consider read replicas for read-heavy workloads

## Maintenance

### Regular Tasks
1. **Security Updates**: Regularly update base images and dependencies
2. **Monitoring**: Review CloudWatch metrics and logs regularly
3. **Backup**: Ensure database backups are configured and tested
4. **Cost Optimization**: Review resource utilization and adjust as needed

### Disaster Recovery
1. **Multi-AZ Deployment**: Deploy across multiple availability zones
2. **Database Backups**: Configure automated RDS backups
3. **Infrastructure as Code**: Use CloudFormation or Terraform for reproducible deployments
4. **Runbook**: Document recovery procedures for common failure scenarios

---

## Support

For issues and questions:
1. Check CloudWatch logs first
2. Review AWS ECS documentation
3. Check application-specific logs and metrics
4. Consult AWS Support if needed

## Additional Resources

- [AWS ECS Developer Guide](https://docs.aws.amazon.com/AmazonECS/latest/developerguide/)
- [AWS Fargate User Guide](https://docs.aws.amazon.com/AmazonECS/latest/userguide/)
- [Spring Boot Production Best Practices](https://docs.spring.io/spring-boot/docs/current/reference/html/deployment.html#deployment)
- [Java in Docker Best Practices](https://developers.redhat.com/blog/2017/03/14/java-inside-docker)
