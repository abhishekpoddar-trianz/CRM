# Build stage
FROM maven:3.9.4-eclipse-temurin-8 AS builder

WORKDIR /workspace

# Copy Maven configuration files first for better caching
COPY pom.xml .

# Download dependencies offline for caching
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:8-jdk

# Create a non-root user
RUN groupadd -r crmuser && useradd -r -g crmuser crmuser

# Set working directory
WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /workspace/target/*.jar app.jar

# Change ownership to non-root user
RUN chown crmuser:crmuser /app/app.jar

# Set JVM options for containerized environments
ENV JAVA_OPTS="-Xmx512m -Xms256m -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Djava.security.egd=file:/dev/./urandom"
ENV SPRING_PROFILES_ACTIVE=docker
ENV TZ=UTC

# Expose the application port
EXPOSE 8080

# Switch to non-root user
USER crmuser

# Run the application
CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]