# Testing GaadiX in Spring Tool Suite (STS) 4.3

## Step 1: Import Project into STS

1. Open STS 4.3
2. File → Import → Maven → Existing Maven Projects
3. Browse to: `D:\KARAD-0825-PGKDAC\Project\webApiApp\gaadix-backend`
4. Select all modules (gaadix-parent, gaadix-common, gaadix-user-service, etc.)
5. Click Finish
6. Wait for Maven to download dependencies (check bottom-right progress)

## Step 2: Build the Project

### Option A: Build All Modules
1. Right-click on `gaadix-parent` (root project)
2. Run As → Maven install
3. Wait for "BUILD SUCCESS" in console

### Option B: Build Individual Modules
1. Right-click on `gaadix-common`
2. Run As → Maven install
3. Wait for success
4. Right-click on `gaadix-user-service`
5. Run As → Maven install

## Step 3: Create Database

### Using MySQL Workbench:
1. Open MySQL Workbench
2. Connect to your local MySQL server
3. Run this query:
```sql
CREATE DATABASE IF NOT EXISTS gaadix_user_db;
```

### Using STS Database Perspective:
1. Window → Perspective → Open Perspective → Other → Database Development
2. Right-click → New → Connection
3. MySQL, username: KD2-GANESH-92393, password: manager
4. Execute: `CREATE DATABASE IF NOT EXISTS gaadix_user_db;`

## Step 4: Run User Service

1. In Project Explorer, expand `gaadix-user-service`
2. Navigate to: `src/main/java/com/gaadix/user/UserServiceApplication.java`
3. Right-click on the file
4. Run As → Spring Boot App

### Expected Console Output:
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

...
Started UserServiceApplication in 8.234 seconds (JVM running for 9.123)
```

## Step 5: Test APIs Using STS HTTP Client

### Method 1: Using STS REST Client

1. Window → Show View → Other → Web → HTTP Client
2. Or use the built-in REST client

### Method 2: Create Test Requests

Create a file: `user-service-tests.http` in your project root:

```http
### 1. Register User
POST http://localhost:8081/api/v1/auth/register
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@gaadix.com",
  "password": "password123",
  "phone": "9876543210"
}

### 2. Login User
POST http://localhost:8081/api/v1/auth/login
Content-Type: application/json

{
  "email": "john@gaadix.com",
  "password": "password123"
}

### 3. Get User by ID (Replace TOKEN)
GET http://localhost:8081/api/v1/users/1
Authorization: Bearer YOUR_ACCESS_TOKEN_HERE

### 4. Get User by Email (Replace TOKEN)
GET http://localhost:8081/api/v1/users/email/john@gaadix.com
Authorization: Bearer YOUR_ACCESS_TOKEN_HERE
```

Right-click on each request → Run As → HTTP Request

## Step 6: Test Using Swagger UI

1. Ensure User Service is running
2. In STS, open Internal Web Browser:
   - Window → Show View → Other → General → Internal Web Browser
3. Navigate to: `http://localhost:8081/swagger-ui/index.html`
4. Test all endpoints interactively

### Testing Flow in Swagger:

1. **Register User**
   - Expand `POST /api/v1/auth/register`
   - Click "Try it out"
   - Fill in the request body
   - Click "Execute"
   - Copy the `accessToken` from response

2. **Authorize**
   - Click "Authorize" button at top
   - Enter: `Bearer YOUR_ACCESS_TOKEN`
   - Click "Authorize"

3. **Test Protected Endpoints**
   - Now you can test GET /api/v1/users/{id}
   - All requests will include the token

## Step 7: Verify Database

### Using STS Data Source Explorer:

1. Window → Show View → Other → Data Management → Data Source Explorer
2. Right-click → New → Connection Profile
3. Select MySQL
4. Enter connection details:
   - Database: gaadix_user_db
   - URL: jdbc:mysql://localhost:3306/gaadix_user_db
   - Username: KD2-GANESH-92393
   - Password: manager
5. Test Connection
6. Expand Tables → users
7. Right-click → Data → Sample Contents

### Expected Tables:
- `users` - User information
- `user_roles` - User role mappings

## Step 8: Debug Mode (Optional)

1. Set breakpoint in `AuthService.java` → `register()` method
2. Right-click `UserServiceApplication.java`
3. Debug As → Spring Boot App
4. Send registration request
5. Debugger will stop at breakpoint
6. Inspect variables, step through code

## Common Issues in STS

### Issue 1: Project Build Errors
**Solution:**
1. Right-click project → Maven → Update Project
2. Check "Force Update of Snapshots/Releases"
3. Click OK

### Issue 2: Port 8081 Already in Use
**Solution:**
1. In Console view, click red square (Terminate) to stop running app
2. Or change port in `application.yml`

### Issue 3: Cannot Connect to Database
**Solution:**
1. Verify MySQL is running (check Services)
2. Test connection in Data Source Explorer
3. Check credentials in `application.yml`

### Issue 4: Dependencies Not Resolved
**Solution:**
1. Right-click `gaadix-common` → Run As → Maven install
2. Then build `gaadix-user-service`
3. Or: Right-click parent → Maven → Update Project (Force Update)

## Verification Checklist

- [ ] All projects imported without errors
- [ ] Maven dependencies downloaded (no red X marks)
- [ ] Database `gaadix_user_db` created
- [ ] User Service starts successfully (port 8081)
- [ ] Console shows "Started UserServiceApplication"
- [ ] Swagger UI accessible at http://localhost:8081/swagger-ui/index.html
- [ ] User registration returns 201 with JWT tokens
- [ ] User login returns 200 with JWT tokens
- [ ] Protected endpoints require Bearer token
- [ ] Database shows encrypted passwords

## Quick Test Sequence

1. ✅ Start User Service in STS
2. ✅ Open Swagger UI in Internal Browser
3. ✅ Register a user → Copy accessToken
4. ✅ Click Authorize → Paste token
5. ✅ Test GET /api/v1/users/1
6. ✅ Verify in database

## Next Steps

Once testing is successful:
- Proceed to Inventory Service implementation
- Test inter-service communication
- Implement remaining microservices

## Tips for STS

- Use Ctrl+Shift+R to quickly open files
- Use Ctrl+Shift+T to find Java types
- Use Ctrl+Space for auto-completion
- Use F3 to navigate to declarations
- Use Ctrl+F11 to run last launched application
- Use F11 to debug last launched application
