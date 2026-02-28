# GaadiX Enterprise E-Car Reselling Platform

## Multi-Module Maven Project Structure

```
gaadix-backend/
├── pom.xml (Parent POM)
├── gaadix-common/                    # Shared entities, DTOs, enums, utilities
├── gaadix-user-service/              # Port 8081 - Authentication, Authorization, RBAC
├── gaadix-inventory-service/         # Port 8082 - Vehicle Management, Images, Inspections
├── gaadix-rto-service/               # Port 8083 - Mock RTO Verification, Compliance
├── gaadix-pricing-service/           # Port 8084 - Dynamic Pricing, Market Trends, Auctions
├── gaadix-fraud-service/             # Port 8085 - Risk Scoring, Anomaly Detection
├── gaadix-payment-service/           # Port 8087 - Transactions, Escrow, Invoices
├── gaadix-analytics-service/         # Port 8088 - Dashboard, Reports, ETL Pipeline
├── gaadix-notification-service/      # Port 8089 - Email, SMS, Push Notifications
├── gaadix-search-service/            # Port 8090 - Advanced Search, Geo-spatial Queries
└── gaadix-gateway/                   # Port 8080 - API Gateway, Routing, Load Balancing
```

## Technology Stack

- **Java**: 21
- **Spring Boot**: 4.0.3
- **Spring Cloud**: 2023.0.3
- **Database**: MySQL 8.x
- **Security**: JWT (jjwt 0.12.3), BCrypt
- **API Docs**: SpringDoc OpenAPI 3.0.1
- **Build Tool**: Maven

## Build Commands

### Build All Modules
```bash
mvn clean install
```

### Build Specific Module
```bash
cd gaadix-user-service
mvn clean install
```

### Run Specific Service
```bash
cd gaadix-user-service
mvn spring-boot:run
```

## Module Dependencies

- All services depend on `gaadix-common`
- Services use Spring Cloud OpenFeign for inter-service communication
- API Gateway routes all external requests to appropriate services

## Next Steps

1. ✅ Multi-module structure created
2. 📋 Create common module (entities, DTOs, enums)
3. 📋 Implement User Service
4. 📋 Implement remaining services
5. 📋 Configure API Gateway
6. 📋 Setup Docker Compose

## Features

- 10 Independent Microservices
- 80+ API Endpoints
- Support for 11 Fuel Types
- Mock RTO Verification System
- 4 AI Modules (Pricing, Fraud Detection, Demand Prediction, Inventory Aging)
- Admin Dashboard with Real-time Metrics
- Advanced Search & Geo-spatial Queries
