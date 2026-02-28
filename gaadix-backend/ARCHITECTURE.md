# GaadiX System Architecture

## Overview
GaadiX is a microservices-based used car marketplace platform built with Spring Boot, MySQL, and modern cloud-native patterns.

## Architecture Diagram

```
                                    ┌─────────────────┐
                                    │   React Web     │
                                    │   Application   │
                                    │  (Port 3000)    │
                                    └────────┬────────┘
                                             │
                                             │ HTTP/REST
                                             ▼
                                    ┌─────────────────┐
                                    │   API Gateway   │
                                    │   (Port 8080)   │
                                    │  - JWT Auth     │
                                    │  - Routing      │
                                    │  - CORS         │
                                    └────────┬────────┘
                                             │
                    ┌────────────────────────┼────────────────────────┐
                    │                        │                        │
         ┌──────────▼──────────┐  ┌─────────▼─────────┐  ┌──────────▼──────────┐
         │   User Service      │  │ Inventory Service │  │  Search Service     │
         │   (Port 8081)       │  │   (Port 8082)     │  │   (Port 8083)       │
         │  - Authentication   │  │  - Vehicles       │  │  - Advanced Search  │
         │  - Authorization    │  │  - Images         │  │  - Geo-spatial      │
         │  - User Management  │  │  - Inspections    │  │  - Filters          │
         └──────────┬──────────┘  └─────────┬─────────┘  └──────────┬──────────┘
                    │                       │                        │
         ┌──────────▼──────────┐  ┌─────────▼─────────┐  ┌──────────▼──────────┐
         │  Payment Service    │  │   RTO Service     │  │  Pricing Service    │
         │   (Port 8084)       │  │   (Port 8085)     │  │   (Port 8086)       │
         │  - Transactions     │  │  - Verification   │  │  - Dynamic Pricing  │
         │  - Refunds          │  │  - Compliance     │  │  - Valuation        │
         └──────────┬──────────┘  └─────────┬─────────┘  └──────────┬──────────┘
                    │                       │                        │
         ┌──────────▼──────────┐  ┌─────────▼─────────┐  ┌──────────▼──────────┐
         │ Notification Service│  │  Fraud Detection  │  │ Analytics Service   │
         │   (Port 8087)       │  │   (Port 8088)     │  │   (Port 8089)       │
         │  - Email/SMS/Push   │  │  - Risk Scoring   │  │  - Business Intel   │
         │  - Multi-channel    │  │  - Recommendations│  │  - Dashboards       │
         └──────────┬──────────┘  └─────────┬─────────┘  └──────────┬──────────┘
                    │                       │                        │
                    └───────────────────────┼────────────────────────┘
                                            │
                                   ┌────────▼────────┐
                                   │  MySQL Cluster  │
                                   │  - 9 Databases  │
                                   │  - Per Service  │
                                   └─────────────────┘
```

## Microservices Details

### 1. User Service (Port 8081)
**Responsibility**: User authentication, authorization, and profile management

**Database**: gaadix_user_db
- Tables: users, user_roles

**Key Features**:
- JWT-based authentication
- Role-based access control (BUYER, SELLER, ADMIN)
- Password encryption (BCrypt)
- User registration and login

**APIs**:
- POST /api/v1/auth/register
- POST /api/v1/auth/login
- GET /api/v1/users/{id}
- GET /api/v1/users/email/{email}

### 2. Inventory Service (Port 8082)
**Responsibility**: Vehicle inventory management

**Database**: gaadix_inventory_db
- Tables: vehicles, vehicle_images, vehicle_inspections

**Key Features**:
- CRUD operations for vehicles
- Image management (multiple images per vehicle)
- 8-point inspection system
- Vehicle status tracking (AVAILABLE, SOLD, RESERVED)

**APIs**: 13 endpoints for vehicles, images, and inspections

### 3. Search Service (Port 8083)
**Responsibility**: Advanced vehicle search and filtering

**Database**: gaadix_search_db
- Tables: vehicle_search_index

**Key Features**:
- 16 filter criteria (brand, model, price, year, fuel, etc.)
- Geo-spatial search (Haversine formula)
- Location-based queries
- Radius-based search

**APIs**:
- GET /api/v1/search/vehicles (with filters)
- GET /api/v1/search/vehicles/nearby

### 4. Payment Service (Port 8084)
**Responsibility**: Payment processing and transaction management

**Database**: gaadix_payment_db
- Tables: payments

**Key Features**:
- 6 payment methods (UPI, Card, Net Banking, etc.)
- Payment lifecycle (PENDING → PROCESSING → COMPLETED)
- Refund processing
- Transaction tracking

**APIs**: 7 endpoints for payment operations

### 5. RTO Service (Port 8085)
**Responsibility**: Vehicle registration verification

**Database**: gaadix_rto_db
- Tables: rto_verifications

**Key Features**:
- Registration number validation
- State extraction
- Mock RTO verification
- Compliance date tracking

**APIs**:
- POST /api/v1/rto/verify
- GET /api/v1/rto/verification/{id}

### 6. Pricing Service (Port 8086)
**Responsibility**: Dynamic vehicle pricing and valuation

**Database**: gaadix_pricing_db
- Tables: pricing_estimates

**Key Features**:
- 15% depreciation per year
- 5-tier odometer adjustment
- ±10% price range calculation
- Market-based pricing

**APIs**:
- GET /api/v1/pricing/estimate

### 7. Notification Service (Port 8087)
**Responsibility**: Multi-channel notifications

**Database**: gaadix_notification_db
- Tables: notifications

**Key Features**:
- Email, SMS, Push notifications
- 7 notification types
- Mock provider integration
- Delivery status tracking

**APIs**:
- POST /api/v1/notifications/send
- GET /api/v1/notifications/user/{userId}

### 8. Fraud Detection Service (Port 8088)
**Responsibility**: Transaction risk assessment

**Database**: gaadix_fraud_db
- Tables: fraud_checks

**Key Features**:
- 5 risk factors (100-point scale)
- 4 risk levels (LOW, MEDIUM, HIGH, CRITICAL)
- Threshold-based recommendations
- Automated fraud detection

**APIs**:
- POST /api/v1/fraud/check
- GET /api/v1/fraud/check/{id}

### 9. Analytics Service (Port 8089)
**Responsibility**: Business intelligence and reporting

**Database**: gaadix_analytics_db
- Tables: analytics_events

**Key Features**:
- Dashboard statistics
- Sales analytics (6-month trends)
- Popular brands tracking
- Event tracking

**APIs**:
- GET /api/v1/analytics/dashboard
- GET /api/v1/analytics/sales
- GET /api/v1/analytics/popular-brands

### 10. API Gateway (Port 8080)
**Responsibility**: Central entry point and routing

**Key Features**:
- Request routing to all services
- JWT authentication filter
- CORS configuration
- Header propagation (X-User-Id, X-User-Email, X-User-Role)

## Technology Stack

### Backend
- **Framework**: Spring Boot 4.0.3
- **Language**: Java 21
- **Build Tool**: Maven 3.8+
- **Security**: Spring Security + JWT
- **Database**: MySQL 8.0
- **ORM**: Spring Data JPA (Hibernate)
- **API Gateway**: Spring Cloud Gateway
- **Documentation**: Swagger/OpenAPI 3.0

### Common Libraries
- **Lombok**: Reduce boilerplate code
- **ModelMapper**: DTO mapping
- **Caffeine**: In-memory caching
- **JJWT**: JWT token handling
- **Validation**: Jakarta Bean Validation

### Database Design
- **Pattern**: Database per service
- **Connection Pool**: HikariCP
- **Credentials**: KD2-GANESH-92393/manager
- **Character Set**: utf8mb4

## Design Patterns

### 1. Microservices Pattern
- Independent services with dedicated databases
- Loose coupling, high cohesion
- Service-specific scaling

### 2. API Gateway Pattern
- Single entry point for all clients
- Centralized authentication
- Request routing and load balancing

### 3. Database per Service
- Each service owns its data
- No direct database access between services
- Data consistency through APIs

### 4. DTO Pattern
- Separation of domain and API models
- Request/Response DTOs
- Data validation at API boundary

### 5. Repository Pattern
- Data access abstraction
- Spring Data JPA repositories
- Query methods and custom queries

## Security Architecture

### Authentication Flow
1. User registers/logs in via User Service
2. User Service generates JWT token (24-hour expiry)
3. Client includes token in Authorization header
4. API Gateway validates token
5. Gateway propagates user info in headers
6. Services use headers for authorization

### JWT Token Structure
```json
{
  "sub": "userId",
  "email": "user@gaadix.com",
  "role": "BUYER",
  "iat": 1234567890,
  "exp": 1234654290
}
```

### Security Features
- Password encryption (BCrypt)
- JWT-based stateless authentication
- Role-based access control
- CORS protection
- SQL injection prevention (JPA)

## Data Flow Examples

### Vehicle Purchase Flow
1. Buyer searches vehicles (Search Service)
2. Views vehicle details (Inventory Service)
3. Gets pricing estimate (Pricing Service)
4. Checks fraud risk (Fraud Detection Service)
5. Verifies RTO (RTO Service)
6. Initiates payment (Payment Service)
7. Receives notification (Notification Service)
8. Event tracked (Analytics Service)

### Vehicle Listing Flow
1. Seller registers (User Service)
2. Creates vehicle listing (Inventory Service)
3. Uploads images (Inventory Service)
4. Inspection scheduled (Inventory Service)
5. Pricing calculated (Pricing Service)
6. Listed in search index (Search Service)
7. Notification sent (Notification Service)

## Scalability Considerations

### Horizontal Scaling
- Stateless services (JWT)
- Load balancer at gateway
- Database read replicas
- Caching layer (Caffeine)

### Performance Optimization
- Connection pooling (HikariCP)
- Query optimization (JPA)
- Lazy loading
- Pagination for large datasets

### Future Enhancements
- Service discovery (Eureka)
- Circuit breaker (Resilience4j)
- Message queue (RabbitMQ/Kafka)
- Distributed tracing (Zipkin)
- Centralized logging (ELK)
- Config server (Spring Cloud Config)

## Port Allocation

| Service | Port | Protocol |
|---------|------|----------|
| API Gateway | 8080 | HTTP |
| User Service | 8081 | HTTP |
| Inventory Service | 8082 | HTTP |
| Search Service | 8083 | HTTP |
| Payment Service | 8084 | HTTP |
| RTO Service | 8085 | HTTP |
| Pricing Service | 8086 | HTTP |
| Notification Service | 8087 | HTTP |
| Fraud Detection Service | 8088 | HTTP |
| Analytics Service | 8089 | HTTP |
| MySQL | 3306 | TCP |

## API Versioning
- All APIs use `/api/v1/` prefix
- Version in URL path
- Backward compatibility maintained
