# CRM Application - AWS ECS Fargate Deployment Guide

This guide provides comprehensive instructions for deploying the Spring Boot CRM application to AWS ECS Fargate.

## Prerequisites and Requirements

### System Requirements
- Docker Desktop installed and running
- AWS CLI v2 installed and configured
- Git for version control
- Java 8 JDK (for local development)
- Maven 3.6+ (for local builds)

### AWS Requirements
- AWS Account with appropriate permissions
- AWS CLI configured with access keys
- VPC with public subnets (minimum 2 for high availability)
- Security groups configured for web traffic
- RDS MySQL database instance (recommended)

### Required IAM Roles
```bash
# ECS Task Execution Role (required for Fargate)
arn:aws:iam::YOUR_ACCOUNT_ID:role/ecsTaskExecutionRole

# ECS Task Role (optional, for task-specific permissions)
arn:aws:iam::YOUR_ACCOUNT_ID:role/ecsTaskRole
```

## Local Development Setup

### 1. Clone and Setup Project
```bash
git clone <your-repo-url>
cd CRM
```

### 2. Local Development with Docker Compose
```bash
# Build and run locally
docker-compose up --build

# Access application
http://localhost:8080
http://localhost:8080/appinfo/health
```

### 3. Local Maven Build
```bash
# Clean and build
mvn clean package

# Run locally
java -jar target/crm-0.0.1-SNAPSHOT.jar
```

## Docker Build and Push

### Build and Push to Registry

#### Linux/macOS:
```bash
chmod +x scripts/build-push.sh
./scripts/build-push.sh
```

#### Windows:
```cmd
scripts\build-push.bat
```

The script will:
1. Prompt for registry selection (ECR or Docker Hub)
2. Handle authentication automatically
3. Build the Docker image with proper tagging
4. Push to the selected registry
5. Display the final image URI for deployment

## AWS ECS Fargate Deployment

### Prerequisites Setup

#### 1. VPC and Networking
```bash
# Create VPC (if not exists)
aws ec2 create-vpc --cidr-block 10.0.0.0/16

# Create public subnets in different AZs
aws ec2 create-subnet --vpc-id vpc-xxx --cidr-block 10.0.1.0/24 --availability-zone us-east-1a
aws ec2 create-subnet --vpc-id vpc-xxx --cidr-block 10.0.2.0/24 --availability-zone us-east-1b

# Create and attach Internet Gateway
aws ec2 create-internet-gateway
aws ec2 attach-internet-gateway --internet-gateway-id igw-xxx --vpc-id vpc-xxx
```

#### 2. Security Groups
```bash
# Create security group allowing HTTP traffic
aws ec2 create-security-group --group-name crm-sg --description "CRM Application Security Group" --vpc-id vpc-xxx

# Allow inbound HTTP traffic
aws ec2 authorize-security-group-ingress --group-id sg-xxx --protocol tcp --port 8080 --cidr 0.0.0.0/0
aws ec2 authorize-security-group-ingress --group-id sg-xxx --protocol tcp --port 80 --cidr 0.0.0.0/0
```

#### 3. RDS Database Setup
```bash
# Create RDS MySQL instance
aws rds create-db-instance \
  --db-instance-identifier crm-mysql \
  --db-instance-class db.t3.micro \
  --engine mysql \
  --master-username admin \
  --master-user-password YourSecurePassword \
  --allocated-storage 20 \
  --vpc-security-group-ids sg-xxx
```

### ECS Fargate Deployment Process

#### 1. Deploy using Scripts

**Linux/macOS:**
```bash
chmod +x scripts/deploy-image.sh
./scripts/deploy-image.sh
```

**Windows:**
```cmd
scripts\deploy-image.bat
```

#### 2. Manual Deployment Steps

If you prefer manual deployment:

```bash
# 1. Create ECS cluster
aws ecs create-cluster --cluster-name crm-cluster

# 2. Register task definition
aws ecs register-task-definition --cli-input-json file://ecs/task-definition.json

# 3. Create service
aws ecs create-service --cli-input-json file://ecs/service-definition.json

# 4. Wait for stability
aws ecs wait services-stable --cluster crm-cluster --services crm-service
```

### ECS Task Definition Configuration

The task definition (`ecs/task-definition.json`) includes:

- **Launch Type**: FARGATE (serverless containers)
- **CPU**: 512 (.5 vCPU)
- **Memory**: 1024 MB
- **Network Mode**: awsvpc (required for Fargate)
- **Port Mapping**: Container port 8080
- **Environment Variables**: Java-specific configurations
- **Logging**: CloudWatch logs to `/ecs/crm`
- **Health Check**: Spring Boot Actuator endpoint

### ECS Service Configuration

The service definition (`ecs/service-definition.json`) includes:

- **Desired Count**: 2 (for high availability)
- **Launch Type**: FARGATE
- **Network Configuration**: Public IP assignment
- **Load Balancer**: Optional Application Load Balancer
- **Deployment Configuration**: Rolling updates
- **Auto Scaling**: Ready for Service Auto Scaling

## CloudWatch Monitoring

### Log Groups
The application logs are sent to CloudWatch Logs:
- **Log Group**: `/ecs/crm`
- **Log Stream**: `ecs/crm/{task-id}`

### Viewing Logs
```bash
# View recent logs
aws logs tail /ecs/crm --follow

# Filter logs
aws logs filter-log-events --log-group-name "/ecs/crm" --filter-pattern "ERROR"
```

### Metrics and Alarms
```bash
# Create CPU utilization alarm
aws cloudwatch put-metric-alarm \
  --alarm-name "crm-high-cpu" \
  --alarm-description "CRM High CPU Utilization" \
  --metric-name CPUUtilization \
  --namespace AWS/ECS \
  --statistic Average \
  --period 300 \
  --threshold 80 \
  --comparison-operator GreaterThanThreshold
```

## Application Configuration

### Environment Variables

Key environment variables used in ECS:

```bash
# JVM Configuration
JAVA_OPTS=-Xmx512m -Xms256m -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0

# Spring Boot Configuration
SPRING_PROFILES_ACTIVE=docker

# Database Configuration
DATABASE_URL=jdbc:mysql://rds-endpoint:3306/crm?useSSL=false
DB_USERNAME=admin
DB_PASSWORD=secure-password

# Timezone
TZ=UTC
```

### Application Properties

The application uses `application.properties` with environment variable substitution:

```properties
# Database Configuration
spring.datasource.url=${DATABASE_URL:jdbc:mysql://localhost:3306/crm?useSSL=false}
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD}

# JPA Configuration
spring.jpa.hibernate.ddl-auto=create-drop

# Actuator Configuration
management.security.enabled=false
management.context-path=/appinfo

# Thymeleaf Configuration
spring.thymeleaf.mode=LEGACYHTML5
spring.thymeleaf.cache=false
```

## Health Checks and Monitoring

### Health Check Endpoints

- **Health Check**: `http://your-alb-dns/appinfo/health`
- **Application Info**: `http://your-alb-dns/appinfo/info`
- **Metrics**: `http://your-alb-dns/appinfo/metrics`

### Container Health Checks

The ECS task definition includes container-level health checks:

```json
"healthCheck": {
  "command": ["CMD-SHELL", "wget --no-verbose --tries=1 --spider http://localhost:8080/appinfo/health || exit 1"],
  "interval": 30,
  "timeout": 5,
  "retries": 3,
  "startPeriod": 60
}
```

## Troubleshooting

### Common Issues and Solutions

#### 1. Task Startup Failures

**Issue**: Tasks fail to start or stop immediately

**Solutions**:
```bash
# Check task logs
aws ecs describe-tasks --cluster crm-cluster --tasks task-id
aws logs tail /ecs/crm

# Common causes:
# - Incorrect image URI
# - Invalid environment variables
# - Network configuration issues
# - Insufficient memory/CPU
```

#### 2. Database Connection Issues

**Issue**: Application cannot connect to database

**Solutions**:
- Verify RDS endpoint and port
- Check security group rules
- Validate database credentials
- Ensure RDS is in same VPC or accessible

#### 3. Load Balancer Issues

**Issue**: ALB health checks failing

**Solutions**:
```bash
# Check target group health
aws elbv2 describe-target-health --target-group-arn arn:aws:elasticloadbalancing:...

# Verify security group allows ALB access
# Check health check path: /appinfo/health
# Ensure health check timeout is appropriate for Java startup
```

#### 4. Performance Issues

**Issue**: Slow application response or high CPU

**Solutions**:
- Increase CPU/memory allocation
- Optimize JVM heap settings
- Enable JVM performance monitoring
- Scale service horizontally

### ECS-Specific Troubleshooting

#### Service Events
```bash
# View service events
aws ecs describe-services --cluster crm-cluster --services crm-service
```

#### Task Definition Issues
```bash
# Validate task definition
aws ecs describe-task-definition --task-definition crm-task
```

#### Network Configuration
```bash
# Check VPC and subnet configuration
aws ec2 describe-subnets --subnet-ids subnet-xxx
aws ec2 describe-security-groups --group-ids sg-xxx
```

## Scaling and Management

### Service Auto Scaling

#### Setup Auto Scaling
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
  --target-tracking-scaling-policy-configuration file://scaling-policy.json
```

#### Scaling Policy Configuration
```json
{
  "TargetValue": 70.0,
  "PredefinedMetricSpecification": {
    "PredefinedMetricType": "ECSServiceAverageCPUUtilization"
  },
  "ScaleOutCooldown": 300,
  "ScaleInCooldown": 300
}
```

### Blue/Green Deployments

#### Using AWS CodeDeploy
```bash
# Create CodeDeploy application
aws deploy create-application \
  --application-name crm-app \
  --compute-platform ECS

# Create deployment group
aws deploy create-deployment-group \
  --application-name crm-app \
  --deployment-group-name crm-dg \
  --service-role-arn arn:aws:iam::account:role/CodeDeployServiceRole \
  --ecs-services serviceName=crm-service,clusterName=crm-cluster
```

### Manual Service Updates

#### Update Service with New Task Definition
```bash
# Update service to use new task definition revision
aws ecs update-service \
  --cluster crm-cluster \
  --service crm-service \
  --task-definition crm-task:2

# Wait for deployment to complete
aws ecs wait services-stable --cluster crm-cluster --services crm-service
```

#### Scale Service
```bash
# Scale up service
aws ecs update-service \
  --cluster crm-cluster \
  --service crm-service \
  --desired-count 5
```

## Security Considerations

### Container Security
- Uses non-root user in container
- Minimal runtime image (eclipse-temurin JRE)
- No unnecessary packages installed
- Secrets managed via AWS Systems Manager or AWS Secrets Manager

### Network Security
- VPC isolation
- Security groups restricting access
- Private subnets for database
- HTTPS termination at load balancer

### IAM Permissions
```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "ecr:GetAuthorizationToken",
        "ecr:BatchCheckLayerAvailability",
        "ecr:GetDownloadUrlForLayer",
        "ecr:BatchGetImage",
        "logs:CreateLogStream",
        "logs:PutLogEvents"
      ],
      "Resource": "*"
    }
  ]
}
```

## Technology-Specific Notes

### Spring Boot Optimizations

1. **JVM Settings for Containers**:
   ```bash
   JAVA_OPTS="-Xmx512m -Xms256m -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"
   ```

2. **Spring Boot Actuator**:
   - Health checks via `/appinfo/health`
   - Metrics available at `/appinfo/metrics`
   - Application info at `/appinfo/info`

3. **Profile-based Configuration**:
   - Uses `SPRING_PROFILES_ACTIVE=docker`
   - Environment-specific properties
   - External configuration via environment variables

### Database Considerations

1. **Connection Pooling**:
   - Spring Boot default HikariCP
   - Optimized for container environments
   - Connection timeout and retry configuration

2. **JPA/Hibernate Configuration**:
   - DDL auto-generation for development
   - Query optimization
   - Connection validation

### Performance Tuning

1. **JVM Performance**:
   ```bash
   # Enable JVM container support
   -XX:+UseContainerSupport
   
   # Set memory percentage
   -XX:MaxRAMPercentage=75.0
   
   # Optimize for startup time
   -XX:TieredStopAtLevel=1
   ```

2. **Spring Boot Optimization**:
   ```properties
   # Reduce startup time
   spring.jpa.hibernate.ddl-auto=validate
   spring.jpa.open-in-view=false
   
   # Optimize web layer
   spring.mvc.async.request-timeout=30000
   ```

## Maintenance and Updates

### Regular Maintenance Tasks

1. **Update Base Images**:
   ```bash
   # Pull latest base image
   docker pull eclipse-temurin:8-jre-alpine
   
   # Rebuild and redeploy
   ./scripts/build-push.sh
   ./scripts/deploy-image.sh
   ```

2. **Monitor Resource Usage**:
   ```bash
   # Check CPU and memory utilization
   aws cloudwatch get-metric-statistics \
     --namespace AWS/ECS \
     --metric-name CPUUtilization \
     --dimensions Name=ServiceName,Value=crm-service Name=ClusterName,Value=crm-cluster
   ```

3. **Log Rotation and Cleanup**:
   ```bash
   # Set CloudWatch log retention
   aws logs put-retention-policy \
     --log-group-name /ecs/crm \
     --retention-in-days 30
   ```

### Version Management

1. **Tag Docker Images**:
   ```bash
   # Use semantic versioning
   docker tag crm:latest crm:v1.2.3
   docker push crm:v1.2.3
   ```

2. **Task Definition Versioning**:
   - Each deployment creates a new task definition revision
   - Keep track of stable revisions for rollback

### Backup and Recovery

1. **Database Backups**:
   ```bash
   # Create RDS snapshot
   aws rds create-db-snapshot \
     --db-instance-identifier crm-mysql \
     --db-snapshot-identifier crm-backup-$(date +%Y%m%d)
   ```

2. **Configuration Backup**:
   - Version control all deployment files
   - Store environment-specific configurations securely
   - Document deployment procedures

---

## Quick Reference

### Useful Commands

```bash
# Build and push
./scripts/build-push.sh

# Deploy to ECS
./scripts/deploy-image.sh

# Check service status
aws ecs describe-services --cluster crm-cluster --services crm-service

# View logs
aws logs tail /ecs/crm --follow

# Scale service
aws ecs update-service --cluster crm-cluster --service crm-service --desired-count 3
```

### Important URLs

- **Application**: `http://your-alb-dns/`
- **Health Check**: `http://your-alb-dns/appinfo/health`
- **Application Info**: `http://your-alb-dns/appinfo/info`
- **Metrics**: `http://your-alb-dns/appinfo/metrics`

### Support and Resources

- AWS ECS Documentation: https://docs.aws.amazon.com/ecs/
- Spring Boot Documentation: https://spring.io/projects/spring-boot
- Docker Best Practices: https://docs.docker.com/develop/dev-best-practices/
- AWS CLI Reference: https://docs.aws.amazon.com/cli/

For additional support, consult the AWS documentation and Spring Boot guides for specific configuration details and troubleshooting steps.