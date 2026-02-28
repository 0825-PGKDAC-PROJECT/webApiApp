# GaadiX API Gateway

Central entry point for all GaadiX microservices. Routes requests to appropriate services with JWT authentication.

## Port
- **8080**

## Features
- Request routing to 9 microservices
- JWT token validation
- CORS configuration
- Request/response logging
- Header propagation (X-User-Id, X-User-Email, X-User-Role)

## Routes

| Service | Port | Paths | Auth Required |
|---------|------|-------|---------------|
| User Service | 8081 | /api/v1/auth/**, /api/v1/users/** | No (auth endpoints) |
| Inventory Service | 8082 | /api/v1/vehicles/**, /api/v1/images/**, /api/v1/inspections/** | Yes |
| Search Service | 8083 | /api/v1/search/** | No |
| Payment Service | 8084 | /api/v1/payments/** | Yes |
| RTO Service | 8085 | /api/v1/rto/** | Yes |
| Pricing Service | 8086 | /api/v1/pricing/** | No |
| Notification Service | 8087 | /api/v1/notifications/** | Yes |
| Fraud Detection Service | 8088 | /api/v1/fraud/** | Yes |
| Analytics Service | 8089 | /api/v1/analytics/** | Yes |

## Usage

### Start Gateway
```bash
mvn spring-boot:run
```

### Access Services via Gateway
```bash
# Register user (no auth)
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"user@gaadix.com","password":"pass123"}'

# Search vehicles (no auth)
curl http://localhost:8080/api/v1/search/vehicles?brand=Maruti

# Get vehicle details (with auth)
curl http://localhost:8080/api/v1/vehicles/1 \
  -H "Authorization: Bearer YOUR_TOKEN"
```

## Configuration
- JWT secret matches User Service
- CORS allows localhost:3000 and localhost:5173
- Debug logging enabled for gateway routes
