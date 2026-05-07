# Multi-stage Docker build for Pricing & Discount Engine

# Stage 1: Build stage
FROM openjdk:11-jdk-slim AS build

# Set working directory
WORKDIR /app

# Copy Gradle wrapper and build files
COPY gradlew .
COPY gradle/ gradle/
COPY build.gradle settings.gradle ./

# Make gradlew executable
RUN chmod +x gradlew

# Download dependencies
RUN ./gradlew dependencies || true

# Copy source code
COPY src/ src/

# Build the application
RUN ./gradlew build -x test

# Stage 2: Runtime stage
FROM openjdk:11-jre-slim AS runtime

# Install required packages
RUN apt-get update && apt-get install -y \
    python3 \
    python3-pip \
    && rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Copy built JAR from build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Copy integration tests
COPY integration_tests/ integration_tests/

# Install Python dependencies for integration tests
RUN pip3 install -r integration_tests/requirements.txt

# Create non-root user for security
RUN groupadd -r appuser && useradd -r -g appuser appuser
RUN chown -R appuser:appuser /app
USER appuser

# Expose application port (if needed for future web interface)
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD java -jar app.jar --version || exit 1

# Default command
CMD ["java", "-jar", "app.jar"]
