# Testing Current Development

## What Can Be Tested Now?

### ✅ Completed Components

1. **Multi-Module Maven Structure**
   - Parent POM with 11 modules
   - Dependency management configured
   - Spring Boot 4.0.3 + Spring Cloud 2023.0.3

2. **Common Module (gaadix-common)**
   - 7 Entities (User, Car, RTOVerification, Transaction, FraudScore, PriceHistory, BaseEntity)
   - 7 Enums (FuelType with 11 types, CarStatus, Transmission, etc.)
   - 3 DTOs (ApiResponse, UserDTO, CarDTO)
   - 3 Exceptions (ResourceNotFoundException, BadRequestException, UnauthorizedException)
   - Utilities and Configuration

3. **User Service (gaadix-user-service)**
   - 8 REST API endpoints
   - JWT authentication (access + refresh tokens)
   - BCrypt password encryption
   - Role-based access control
   - Account locking mechanism
   - User CRUD operations

## Quick Test Steps

### Option 1: Automated Script
```bash
# Run the quick start script
quick-start.bat

# Then in a new terminal, start the service
cd gaadix-user-service
mvn spring-boot:run

# In another terminal, run tests
test-user-service.bat
```

### Option 2: Manual Testing

#### 1. Setup Database
```bash
mysql -u KD2-GANESH-92393 -pmanager -e "CREATE DATABASE IF NOT EXISTS gaadix_user_db;"
```

#### 2. Build Project
```bash
# Build common module first
cd gaadix-common
mvn clean install -DskipTests

# Build user service
cd ../gaadix-user-service
mvn clean install -DskipTests
```

#### 3. Run User Service
```bash
cd gaadix-user-service
mvn spring-boot:run
```

Wait for: `Started UserServiceApplication in X seconds (JVM running for Y)`

#### 4. Test Registration (New Terminal)
```bash
curl -X POST http://localhost:8081/api/v1/auth/register ^
  -H "Content-Type: application/json" ^
  -d "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john@gaadix.com\",\"password\":\"password123\",\"phone\":\"9876543210\"}"
```

#### 5. Test Login
```bash
curl -X POST http://localhost:8081/api/v1/auth/login ^
  -H "Content-Type: application/json" ^
  -d "{\"email\":\"john@gaadix.com\",\"password\":\"password123\"}"
```

Copy the `accessToken` from response.

#### 6. Test Protected Endpoint
```bash
curl -X GET http://localhost:8081/api/v1/users/1 ^
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

### Option 3: Swagger UI
1. Start User Service
2. Open browser: http://localhost:8081/swagger-ui/index.html
3. Test all endpoints interactively

## Expected Results

### ✅ Success Indicators

1. **Service Starts Successfully**
   - No errors in console
   - Port 8081 is listening
   - Database tables auto-created

2. **Registration Works**
   - Returns 201 Created
   - Returns JWT tokens
   - User saved in database
   - Password is encrypted

3. **Login Works**
   - Returns 200 OK
   - Returns valid JWT tokens
   - Updates last login timestamp

4. **Authentication Works**
   - Protected endpoints require token
   - Invalid token returns 403
   - Valid token allows access

5. **Database Verification**
```bash
mysql -u KD2-GANESH-92393 -pmanager gaadix_user_db -e "SELECT * FROM users;"
```
Should show registered users with encrypted passwords.

## What Cannot Be Tested Yet?

❌ **Not Yet Implemented:**
- Inventory Service (car listings)
- RTO Service (vehicle verification)
- Pricing Service (dynamic pricing)
- Fraud Detection Service
- Payment Service
- Analytics Service
- Notification Service
- Search Service
- API Gateway (routing)

## Troubleshooting

### Issue: Port 8081 already in use
```bash
netstat -ano | findstr :8081
taskkill /PID <PID> /F
```

### Issue: MySQL connection failed
- Verify MySQL is running
- Check credentials in application.yml
- Ensure database exists

### Issue: Build fails
```bash
# Clean everything
mvn clean

# Rebuild from parent
cd gaadix-backend
mvn clean install -DskipTests
```

### Issue: Module not found
Ensure you build in order:
1. gaadix-common (first)
2. gaadix-user-service (depends on common)

## Verification Checklist

- [ ] MySQL database created
- [ ] Common module builds successfully
- [ ] User service builds successfully
- [ ] User service starts on port 8081
- [ ] Swagger UI accessible
- [ ] User registration works
- [ ] User login returns JWT tokens
- [ ] Protected endpoints require authentication
- [ ] Database tables created automatically
- [ ] Passwords are encrypted in database

## Next Steps After Testing

Once User Service is verified:
1. ✅ Commit test scripts
2. 🔄 Proceed to Inventory Service
3. 🔄 Implement RTO Service
4. 🔄 Continue with remaining services

## Support

For detailed testing instructions, see: `TESTING.md`
