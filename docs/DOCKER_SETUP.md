# Docker Setup Guide and Best Practices

## 🐳 Docker Configuration Overview

This project includes a multi-stage Docker configuration for building and running the Pricing & Discount Engine in a containerized environment.

## 📋 Dockerfile Features

### Multi-Stage Build
- **Stage 1 (Build)**: Compiles Java application using OpenJDK 11
- **Stage 2 (Runtime)**: Lightweight runtime environment with JRE

### Security Best Practices
- **Non-root user**: Application runs as `appuser`
- **Minimal base images**: Uses `slim` variants for smaller footprint
- **Health checks**: Built-in container health monitoring

### Integration Testing Support
- **Python environment**: Includes Python 3 and pip
- **Test dependencies**: Installs pytest and other testing libraries
- **Test execution**: Ready for integration test execution

## 🚀 Quick Start with Docker

### Build the Image
```bash
# Build the Docker image
docker build -t pricing-engine .

# Build with custom tag
docker build -t pricing-engine:latest .
```

### Run the Container
```bash
# Run the application
docker run --rm pricing-engine

# Run with custom name
docker run --name pricing-app --rm pricing-engine

# Run in interactive mode
docker run -it --rm pricing-engine bash
```

### Run Integration Tests
```bash
# Run tests in container
docker run --rm pricing-engine python3 -m pytest integration_tests/

# Run with volume for test results
docker run --rm -v $(pwd)/test-results:/app/test-results \
  pricing-engine python3 -m pytest integration_tests/ -v
```

## 🏗️ Dockerfile Breakdown

### Stage 1: Build Stage
```dockerfile
FROM openjdk:11-jdk-slim AS build
WORKDIR /app
COPY gradlew gradle/ build.gradle settings.gradle ./
RUN chmod +x gradlew
RUN ./gradlew dependencies || true
COPY src/ src/
RUN ./gradlew build -x test
```

**Purpose**: Compile Java application
- Downloads Gradle dependencies
- Builds JAR without running tests
- Optimized for caching with layer ordering

### Stage 2: Runtime Stage
```dockerfile
FROM openjdk:11-jre-slim AS runtime
RUN apt-get update && apt-get install -y python3 python3-pip
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
COPY integration_tests/ integration_tests/
RUN pip3 install -r integration_tests/requirements.txt
RUN groupadd -r appuser && useradd -r -g appuser appuser
RUN chown -R appuser:appuser /app
USER appuser
EXPOSE 8080
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD java -jar app.jar --version || exit 1
CMD ["java", "-jar", "app.jar"]
```

**Purpose**: Runtime environment
- Lightweight JRE for execution
- Python environment for integration tests
- Security hardening with non-root user
- Health monitoring capabilities

## 🔧 Docker Compose Configuration

### Development Environment
Create `docker-compose.yml` for development:

```yaml
version: '3.8'

services:
  pricing-engine:
    build: .
    container_name: pricing-app-dev
    ports:
      - "8080:8080"
    volumes:
      - ./src:/app/src
      - ./build.gradle:/app/build.gradle
      - ./settings.gradle:/app/settings.gradle
    environment:
      - JAVA_OPTS=-Xmx512m
    command: ./gradlew run
    user: "1000:1000"

  integration-tests:
    build: .
    container_name: pricing-tests
    volumes:
      - ./test-results:/app/test-results
    command: python3 -m pytest integration_tests/ -v --junitxml=/app/test-results/results.xml
    depends_on:
      - pricing-engine
```

### Production Environment
Create `docker-compose.prod.yml` for production:

```yaml
version: '3.8'

services:
  pricing-engine:
    build: .
    container_name: pricing-app-prod
    ports:
      - "8080:8080"
    environment:
      - JAVA_OPTS=-Xmx1g -XX:+UseG1GC
    restart: unless-stopped
    healthcheck:
      test: ["CMD", "java", "-jar", "app.jar", "--health-check"]
      interval: 30s
      timeout: 10s
      retries: 3
      start_period: 40s
    logging:
      driver: "json-file"
      options:
        max-size: "10m"
        max-file: "3"
```

## 📊 Docker Optimization

### Image Size Optimization
- **Multi-stage builds**: Reduces final image size by ~50%
- **Slim base images**: Uses minimal OS packages
- **Layer caching**: Optimizes build times with proper layer ordering

### Security Hardening
- **Non-root user**: Reduces security attack surface
- **Minimal packages**: Only installs necessary dependencies
- **Health checks**: Monitors application health

### Performance Optimization
- **JVM tuning**: Configurable memory settings
- **Resource limits**: Container resource management
- **Monitoring**: Built-in health and performance metrics

## 🧪 Testing with Docker

### Unit Tests in Container
```bash
# Run unit tests
docker run --rm pricing-engine ./gradlew test

# Run with test reports
docker run --rm -v $(pwd)/test-reports:/app/test-reports \
  pricing-engine ./gradlew test --reports-dir=/app/test-reports
```

### Integration Tests
```bash
# Run integration tests
docker run --rm pricing-engine python3 -m pytest integration_tests/

# Run with coverage
docker run --rm pricing-engine python3 -m pytest \
  integration_tests/ --cov=pricing --cov-report=html
```

### End-to-End Testing
```bash
# Run complete test suite
docker-compose -f docker-compose.test.yml up --abort-on-container-exit
```

## 🔍 Docker Commands Reference

### Build Commands
```bash
# Build without cache
docker build --no-cache -t pricing-engine .

# Build with build arguments
docker build --build-arg JAVA_VERSION=11 -t pricing-engine .

# Build for specific platform
docker build --platform linux/amd64 -t pricing-engine .
```

### Run Commands
```bash
# Run with environment variables
docker run -e JAVA_OPTS="-Xmx1g" pricing-engine

# Run with port mapping
docker run -p 8080:8080 pricing-engine

# Run with volume mount
docker run -v $(pwd)/logs:/app/logs pricing-engine

# Run detached
docker run -d --name pricing-app pricing-engine
```

### Management Commands
```bash
# View logs
docker logs pricing-app

# Follow logs
docker logs -f pricing-app

# Inspect container
docker inspect pricing-app

# Execute commands in container
docker exec -it pricing-app bash

# Stop container
docker stop pricing-app

# Remove container
docker rm pricing-app

# Remove image
docker rmi pricing-engine
```

## 🚨 Troubleshooting

### Common Issues

#### Build Failures
```bash
# Clean build
docker system prune -f
docker build --no-cache -t pricing-engine .

# Debug build process
docker build --progress=plain -t pricing-engine .
```

#### Permission Issues
```bash
# Fix file permissions
docker run --user root pricing-engine chown -R appuser:appuser /app

# Run with current user
docker run --user $(id -u):$(id -g) pricing-engine
```

#### Memory Issues
```bash
# Increase memory
docker run --memory=1g pricing-engine

# Check memory usage
docker stats pricing-app
```

### Debug Mode
```bash
# Run with debug enabled
docker run -e JAVA_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005" \
  -p 5005:5005 pricing-engine

# Interactive debugging
docker run -it --entrypoint bash pricing-engine
```

## 📈 Performance Monitoring

### Container Metrics
```bash
# Real-time stats
docker stats pricing-app

# Resource usage history
docker stats --no-stream pricing-app

# Detailed inspection
docker inspect pricing-app | grep -A 10 "Stats"
```

### Application Monitoring
```bash
# Health check status
docker inspect pricing-app | grep Health

# Log analysis
docker logs pricing-app | grep ERROR

# Performance profiling
docker run --cpus="1.0" --memory="512m" pricing-engine
```

## 🔐 Security Best Practices

### Image Security
```bash
# Scan for vulnerabilities
docker scan pricing-engine

# Use trusted base images
docker pull openjdk:11-jre-slim@sha256:<hash>

# Sign images
docker trust sign pricing-engine:latest
```

### Runtime Security
```bash
# Run with read-only filesystem
docker run --read-only --tmpfs /tmp pricing-engine

# Drop capabilities
docker run --cap-drop ALL pricing-engine

# Use seccomp profile
docker run --security-opt seccomp=default pricing-engine
```

## 🚀 CI/CD Integration

### GitHub Actions Example
```yaml
name: Build and Test

on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v2
    - name: Build Docker image
      run: docker build -t pricing-engine .
    - name: Run tests
      run: |
        docker run --rm pricing-engine ./gradlew test
        docker run --rm pricing-engine python3 -m pytest integration_tests/
```

### Jenkins Pipeline Example
```groovy
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'docker build -t pricing-engine .'
            }
        }
        stage('Test') {
            steps {
                sh 'docker run --rm pricing-engine ./gradlew test'
                sh 'docker run --rm pricing-engine python3 -m pytest integration_tests/'
            }
        }
    }
}
```

## 📚 Additional Resources

### Docker Documentation
- [Dockerfile Best Practices](https://docs.docker.com/develop/develop-images/dockerfile_best-practices/)
- [Multi-Stage Builds](https://docs.docker.com/build/building/multi-stage/)
- [Docker Compose](https://docs.docker.com/compose/)

### Java in Docker
- [Oracle Docker Guidelines](https://docs.oracle.com/en/java/java-se/13/docker/)
- [OpenJDK Docker Images](https://hub.docker.com/_/openjdk)

### Security
- [Docker Security Best Practices](https://docs.docker.com/engine/security/)
- [Container Security Checklist](https://cheatsheetseries.owasp.org/cheatsheets/Docker_Security_Cheat_Sheet.html)

---

This Docker configuration provides a production-ready setup for the Pricing & Discount Engine with security, performance, and monitoring considerations built-in.
