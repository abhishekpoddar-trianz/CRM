# Multi-stage Docker build for cloud deployment
FROM openjdk:11-jdk-slim as builder

WORKDIR /app

# Copy Maven files
COPY pom.xml .
COPY .mvn/ .mvn/
COPY mvnw .

# Make mvnw executable
RUN chmod +x ./mvnw

# Download dependencies first for better Docker layer caching
RUN ./mvnw dependency:resolve dependency:resolve-sources

# Copy source code
COPY src/ src/

# Build the application
RUN ./mvnw clean package -DskipTests

# Production stage
FROM openjdk:11-jre-slim

# Install necessary tools and create non-root user
RUN apt-get update && \
    apt-get install -y curl && \
    rm -rf /var/lib/apt/lists/* && \
    useradd --create-home --shell /bin/bash crm

# Set working directory
WORKDIR /app

# Copy built application
COPY --from=builder /app/target/*.jar app.jar

# Change ownership to non-root user
RUN chown -R crm:crm /app

# Switch to non-root user
USER crm

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:${PORT:-8080}/actuator/health || exit 1

# Set default JVM options for cloud deployment
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -Dspring.profiles.active=cloud"

# Expose port
EXPOSE 8080

# Start the application
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]