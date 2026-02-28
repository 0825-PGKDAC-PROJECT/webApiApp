# Quick Start - Testing in STS 4.3

## 🚀 5-Minute Setup

### Step 1: Import Project (2 min)
```
STS → File → Import → Maven → Existing Maven Projects
Browse to: D:\KARAD-0825-PGKDAC\Project\webApiApp\gaadix-backend
Select All → Finish
Wait for dependencies to download (watch bottom-right)
```

### Step 2: Create Database (30 sec)
```
Open MySQL Workbench or Command Prompt:
CREATE DATABASE IF NOT EXISTS gaadix_user_db;
```

### Step 3: Build Project (1 min)
```
In STS Project Explorer:
Right-click "gaadix-common" → Run As → Maven install
Wait for BUILD SUCCESS
Right-click "gaadix-user-service" → Run As → Maven install
Wait for BUILD SUCCESS
```

### Step 4: Run Service (30 sec)
```
Navigate to: gaadix-user-service/src/main/java/com/gaadix/user/UserServiceApplication.java
Right-click → Run As → Spring Boot App
Wait for: "Started UserServiceApplication in X seconds"
```

### Step 5: Test with Swagger (1 min)
```
In STS:
Window → Show View → Other → General → Internal Web Browser
Navigate to: http://localhost:8081/swagger-ui/index.html

Test Flow:
1. POST /api/v1/auth/register → Execute → Copy accessToken
2. Click "Authorize" button → Enter: Bearer YOUR_TOKEN
3. GET /api/v1/users/1 → Execute → Should return user data
```

## ✅ Success Indicators

Console shows:
```
Started UserServiceApplication in 8.234 seconds (JVM running for 9.123)
```

Swagger UI loads at: http://localhost:8081/swagger-ui/index.html

Registration returns:
```json
{
  "success": true,
  "message": "User registered successfully",
  "data": {
    "accessToken": "eyJhbGc...",
    "user": {...}
  }
}
```

## 🔧 Troubleshooting

### Red X on Projects?
```
Right-click project → Maven → Update Project
Check "Force Update" → OK
```

### Port 8081 in use?
```
In Console → Click red square (Terminate)
Or change port in application.yml
```

### Can't connect to database?
```
Verify MySQL is running
Check Windows Services → MySQL
```

## 📝 Test Sequence

1. Register user → Get token
2. Login → Verify token works
3. Get user by ID → Verify authentication
4. Check database → Verify data saved

## 🎯 What to Verify

- [ ] Service starts without errors
- [ ] Swagger UI accessible
- [ ] User registration works
- [ ] JWT tokens generated
- [ ] Login successful
- [ ] Protected endpoints require token
- [ ] Database has user record
- [ ] Password is encrypted

## 📂 Files to Use

- `TESTING-STS.md` - Detailed STS instructions
- `user-service-tests.http` - HTTP test requests
- `TESTING.md` - Complete testing guide

## 🎓 STS Tips

- **Run**: Ctrl+F11
- **Debug**: F11
- **Find File**: Ctrl+Shift+R
- **Find Type**: Ctrl+Shift+T
- **Auto-complete**: Ctrl+Space

## ⏭️ Next

Once User Service works:
→ Proceed to Inventory Service (Step 4)
