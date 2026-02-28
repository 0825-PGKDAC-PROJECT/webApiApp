# GaadiX Testing Guide

## Prerequisites

### 1. MySQL Setup
Ensure MySQL is running and database is created:
```bash
mysql -u KD2-GANESH-92393 -pmanager -e "CREATE DATABASE IF NOT EXISTS gaadix_user_db;"
```

### 2. Build the Project
```bash
cd gaadix-backend
mvn clean install -DskipTests
```

## Running the Services

### Start User Service (Port 8081)
```bash
cd gaadix-user-service
mvn spring-boot:run
```

Wait for the message: "Started UserServiceApplication in X seconds"

## Testing User Service APIs

### 1. Register a New User
```bash
curl -X POST http://localhost:8081/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john@gaadix.com\",\"password\":\"password123\",\"phone\":\"9876543210\"}"
```

**Expected Response:**
```json
{
  "success": true,
  "message": "User registered successfully",
  "data": {
    "accessToken": "eyJhbGc...",
    "refreshToken": "eyJhbGc...",
    "tokenType": "Bearer",
    "user": {
      "id": 1,
      "firstName": "John",
      "lastName": "Doe",
      "email": "john@gaadix.com",
      "phone": "9876543210",
      "isActive": true,
      "roles": ["BUYER"]
    }
  }
}
```

### 2. Login
```bash
curl -X POST http://localhost:8081/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d "{\"email\":\"john@gaadix.com\",\"password\":\"password123\"}"
```

**Expected Response:**
```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "accessToken": "eyJhbGc...",
    "refreshToken": "eyJhbGc...",
    "tokenType": "Bearer",
    "user": {...}
  }
}
```

### 3. Get User by ID (Requires Token)
```bash
# Replace YOUR_TOKEN with the accessToken from login response
curl -X GET http://localhost:8081/api/v1/users/1 \
  -H "Authorization: Bearer YOUR_TOKEN"
```

### 4. Get User by Email (Requires Token)
```bash
curl -X GET http://localhost:8081/api/v1/users/email/john@gaadix.com \
  -H "Authorization: Bearer YOUR_TOKEN"
```

### 5. Update User (Requires Token)
```bash
curl -X PUT http://localhost:8081/api/v1/users/1 \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d "{\"firstName\":\"John\",\"lastName\":\"Smith\",\"phone\":\"9876543210\"}"
```

### 6. Refresh Token
```bash
# Replace YOUR_REFRESH_TOKEN with the refreshToken from login
curl -X POST "http://localhost:8081/api/v1/auth/refresh?refreshToken=YOUR_REFRESH_TOKEN"
```

### 7. Logout
```bash
curl -X POST http://localhost:8081/api/v1/auth/logout \
  -H "Authorization: Bearer YOUR_TOKEN"
```

## Testing with Postman

### Import Collection
1. Open Postman
2. Create new collection "GaadiX User Service"
3. Add requests with above endpoints
4. Use environment variables for token management

### Environment Variables
- `base_url`: http://localhost:8081
- `access_token`: (auto-set from login response)
- `refresh_token`: (auto-set from login response)

## Testing Error Scenarios

### 1. Duplicate Email Registration
```bash
# Register same email twice - should fail
curl -X POST http://localhost:8081/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d "{\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"email\":\"john@gaadix.com\",\"password\":\"password123\",\"phone\":\"9876543211\"}"
```

**Expected:** 400 Bad Request - "Email already registered"

### 2. Invalid Login Credentials
```bash
curl -X POST http://localhost:8081/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d "{\"email\":\"john@gaadix.com\",\"password\":\"wrongpassword\"}"
```

**Expected:** 401 Unauthorized - "Invalid credentials"

### 3. Access Without Token
```bash
curl -X GET http://localhost:8081/api/v1/users/1
```

**Expected:** 403 Forbidden

### 4. Invalid Token
```bash
curl -X GET http://localhost:8081/api/v1/users/1 \
  -H "Authorization: Bearer invalid_token"
```

**Expected:** 403 Forbidden

## Verify Database

### Check Users Table
```bash
mysql -u KD2-GANESH-92393 -pmanager gaadix_user_db -e "SELECT id, first_name, last_name, email, phone, is_active FROM users;"
```

### Check User Roles
```bash
mysql -u KD2-GANESH-92393 -pmanager gaadix_user_db -e "SELECT * FROM user_roles;"
```

## Swagger UI

Access API documentation at:
```
http://localhost:8081/swagger-ui/index.html
```

## Health Check

```bash
curl http://localhost:8081/actuator/health
```

## Common Issues

### Issue 1: Port Already in Use
**Solution:** Change port in `application.yml` or kill process on port 8081
```bash
netstat -ano | findstr :8081
taskkill /PID <PID> /F
```

### Issue 2: Database Connection Failed
**Solution:** Verify MySQL is running and credentials are correct
```bash
mysql -u KD2-GANESH-92393 -pmanager -e "SHOW DATABASES;"
```

### Issue 3: Build Failures
**Solution:** Clean and rebuild
```bash
mvn clean install -DskipTests
```

## Success Criteria

✅ User registration works  
✅ User login returns JWT tokens  
✅ Protected endpoints require authentication  
✅ Token validation works  
✅ Database tables created automatically  
✅ Password is encrypted (BCrypt)  
✅ Account locks after 5 failed attempts  
✅ Swagger UI accessible  

## Next Steps

Once User Service is verified:
1. Test with different user roles (ADMIN, SELLER, BUYER)
2. Test account locking mechanism
3. Test token expiration
4. Proceed to Inventory Service implementation
