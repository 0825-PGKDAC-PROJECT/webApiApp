# GaadiX User Service

Authentication, Authorization, and User Management Service.

## Port
8081

## Database
gaadix_user_db

## Features
- ✅ User Registration & Login
- ✅ JWT Authentication (Access + Refresh Tokens)
- ✅ BCrypt Password Encryption (Strength 12)
- ✅ Role-Based Access Control (RBAC)
- ✅ Account Locking (5 failed attempts)
- ✅ User Profile Management
- ✅ Method-level Security

## API Endpoints (8)

### Authentication
- `POST /api/v1/auth/register` - Register new user
- `POST /api/v1/auth/login` - Login user
- `POST /api/v1/auth/refresh` - Refresh access token
- `POST /api/v1/auth/logout` - Logout user

### User Management
- `GET /api/v1/users/{id}` - Get user by ID
- `GET /api/v1/users/email/{email}` - Get user by email
- `GET /api/v1/users` - Get all users (Admin only)
- `PUT /api/v1/users/{id}` - Update user
- `DELETE /api/v1/users/{id}` - Delete user (Admin only)

## User Roles
- ADMIN - Full system access
- SELLER - Can list cars
- BUYER - Can purchase cars
- DEALER - Bulk operations
- INSPECTOR - Vehicle inspection

## Security
- JWT tokens with HS512 algorithm
- Access token: 24 hours
- Refresh token: 7 days
- Stateless session management
- CORS enabled

## Run
```bash
cd gaadix-user-service
mvn spring-boot:run
```

## Test
```bash
# Register
curl -X POST http://localhost:8081/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{"firstName":"John","lastName":"Doe","email":"john@example.com","password":"password123","phone":"9876543210"}'

# Login
curl -X POST http://localhost:8081/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"john@example.com","password":"password123"}'
```
