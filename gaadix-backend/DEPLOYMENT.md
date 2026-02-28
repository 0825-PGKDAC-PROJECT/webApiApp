# GaadiX Deployment Guide

## Prerequisites

### Required Software
- **Java 21** (OpenJDK or Oracle JDK)
- **Maven 3.8+**
- **MySQL 8.0+**
- **Git**
- **Spring Tool Suite 4.3** (optional, for development)

### System Requirements
- **RAM**: Minimum 8GB (16GB recommended)
- **Disk Space**: 5GB free space
- **OS**: Windows 10/11, Linux, macOS

## Local Development Setup

### 1. Clone Repository
```bash
git clone <repository-url>
cd gaadix-backend
```

### 2. Setup MySQL Databases
```bash
# Login to MySQL
mysql -u root -p

# Run database creation script
source database-scripts/00-create-databases.sql
```

### 3. Build All Services
```bash
# Build parent and common module first
mvn clean install -DskipTests

# Or build individual services
cd gaadix-common && mvn clean install
cd ../gaadix-user-service && mvn clean install
```

### 4. Start Services (Sequential Order)

**Terminal 1 - User Service:**
```bash
cd gaadix-user-service
mvn spring-boot:run
```

**Terminal 2 - Inventory Service:**
```bash
cd gaadix-inventory-service
mvn spring-boot:run
```

**Terminal 3 - Search Service:**
```bash
cd gaadix-search-service
mvn spring-boot:run
```

**Terminal 4 - Payment Service:**
```bash
cd gaadix-payment-service
mvn spring-boot:run
```

**Terminal 5 - RTO Service:**
```bash
cd gaadix-rto-service
mvn spring-boot:run
```

**Terminal 6 - Pricing Service:**
```bash
cd gaadix-pricing-service
mvn spring-boot:run
```

**Terminal 7 - Notification Service:**
```bash
cd gaadix-notification-service
mvn spring-boot:run
```

**Terminal 8 - Fraud Detection Service:**
```bash
cd gaadix-fraud-service
mvn spring-boot:run
```

**Terminal 9 - Analytics Service:**
```bash
cd gaadix-analytics-service
mvn spring-boot:run
```

**Terminal 10 - API Gateway:**
```bash
cd gaadix-api-gateway
mvn spring-boot:run
```

### 5. Load Sample Data
```bash
mysql -u KD2-GANESH-92393 -p < database-scripts/01-sample-data.sql
```

### 6. Verify Services
```bash
# Check all services are running
curl http://localhost:8080/actuator/health  # API Gateway
curl http://localhost:8081/actuator/health  # User Service
curl http://localhost:8082/actuator/health  # Inventory Service
# ... and so on
```

## Docker Deployment

### 1. Create Dockerfile for Each Service

**Example: gaadix-user-service/Dockerfile**
```dockerfile
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/gaadix-user-service-*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### 2. Create docker-compose.yml

```yaml
version: '3.8'

services:
  mysql:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: manager
      MYSQL_USER: KD2-GANESH-92393
      MYSQL_PASSWORD: manager
    ports:
      - "3306:3306"
    volumes:
      - mysql-data:/var/lib/mysql
      - ./database-scripts:/docker-entrypoint-initdb.d

  user-service:
    build: ./gaadix-user-service
    ports:
      - "8081:8081"
    depends_on:
      - mysql
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/gaadix_user_db

  inventory-service:
    build: ./gaadix-inventory-service
    ports:
      - "8082:8082"
    depends_on:
      - mysql

  api-gateway:
    build: ./gaadix-api-gateway
    ports:
      - "8080:8080"
    depends_on:
      - user-service
      - inventory-service

volumes:
  mysql-data:
```

### 3. Build and Run
```bash
docker-compose up --build
```

## Kubernetes Deployment

### 1. Create Kubernetes Manifests

**mysql-deployment.yaml**
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: mysql
spec:
  replicas: 1
  selector:
    matchLabels:
      app: mysql
  template:
    metadata:
      labels:
        app: mysql
    spec:
      containers:
      - name: mysql
        image: mysql:8.0
        env:
        - name: MYSQL_ROOT_PASSWORD
          value: "manager"
        ports:
        - containerPort: 3306
---
apiVersion: v1
kind: Service
metadata:
  name: mysql
spec:
  ports:
  - port: 3306
  selector:
    app: mysql
```

**user-service-deployment.yaml**
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: user-service
spec:
  replicas: 2
  selector:
    matchLabels:
      app: user-service
  template:
    metadata:
      labels:
        app: user-service
    spec:
      containers:
      - name: user-service
        image: gaadix/user-service:latest
        ports:
        - containerPort: 8081
        env:
        - name: SPRING_DATASOURCE_URL
          value: "jdbc:mysql://mysql:3306/gaadix_user_db"
---
apiVersion: v1
kind: Service
metadata:
  name: user-service
spec:
  type: LoadBalancer
  ports:
  - port: 8081
  selector:
    app: user-service
```

### 2. Deploy to Kubernetes
```bash
kubectl apply -f k8s/mysql-deployment.yaml
kubectl apply -f k8s/user-service-deployment.yaml
kubectl apply -f k8s/api-gateway-deployment.yaml
```

## Production Deployment Checklist

- [ ] Change default passwords in application.yml
- [ ] Update JWT secret key
- [ ] Configure production database URLs
- [ ] Enable HTTPS/SSL certificates
- [ ] Set up monitoring (Prometheus/Grafana)
- [ ] Configure log aggregation (ELK Stack)
- [ ] Set up backup strategy for databases
- [ ] Configure auto-scaling policies
- [ ] Set up CI/CD pipeline
- [ ] Enable rate limiting on API Gateway
- [ ] Configure CORS for production domains
- [ ] Set up health checks and readiness probes
- [ ] Configure resource limits (CPU/Memory)
- [ ] Enable distributed tracing (Zipkin/Jaeger)

## Environment Variables

### User Service
```
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/gaadix_user_db
SPRING_DATASOURCE_USERNAME=KD2-GANESH-92393
SPRING_DATASOURCE_PASSWORD=manager
JWT_SECRET=your-secret-key
JWT_EXPIRATION=86400000
```

### API Gateway
```
SPRING_CLOUD_GATEWAY_ROUTES_0_URI=http://user-service:8081
SPRING_CLOUD_GATEWAY_ROUTES_1_URI=http://inventory-service:8082
JWT_SECRET=your-secret-key
```

## Monitoring Endpoints

All services expose actuator endpoints:
- `/actuator/health` - Health check
- `/actuator/metrics` - Metrics
- `/actuator/info` - Service info

## Troubleshooting

### Service Won't Start
1. Check if port is already in use: `netstat -ano | findstr :8081`
2. Verify database connection
3. Check logs: `tail -f logs/application.log`

### Database Connection Failed
1. Verify MySQL is running
2. Check credentials in application.yml
3. Ensure database exists

### Out of Memory
1. Increase JVM heap: `java -Xmx2g -jar app.jar`
2. Check for memory leaks
3. Enable GC logging

## Performance Tuning

### JVM Options
```bash
java -Xms512m -Xmx2g -XX:+UseG1GC -jar app.jar
```

### Database Connection Pool
```yaml
spring:
  datasource:
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5
      connection-timeout: 30000
```

### Caching
```yaml
spring:
  cache:
    type: caffeine
    caffeine:
      spec: maximumSize=1000,expireAfterWrite=10m
```
