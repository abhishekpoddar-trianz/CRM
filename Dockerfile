# Multi-stage build for Spring Boot CRM application
FROM maven:3.9.4-eclipse-temurin-8 AS builder

# Set working directory
WORKDIR /workspace

# Copy pom.xml first for dependency caching
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:8-jre-alpine

# Create non-root user for security
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

# Set working directory
WORKDIR /app

# Copy JAR from builder stage
COPY --from=builder /workspace/target/*.jar app.jar

# Change ownership to non-root user
RUN chown -R appuser:appgroup /app

# Switch to non-root user
USER appuser

# Set JVM options for containerized environment
ENV JAVA_OPTS="-Xmx512m -Xms256m -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Djava.security.egd=file:/dev/./urandom"

# Set Spring profile
ENV SPRING_PROFILES_ACTIVE=docker

# Set timezone
ENV TZ=UTC

# Expose port
EXPOSE 8080

# Run the application
CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]