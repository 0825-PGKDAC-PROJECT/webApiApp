@echo off
echo ========================================
echo GaadiX User Service API Tests
echo ========================================
echo.

echo 1. Testing User Registration...
curl -X POST http://localhost:8081/api/v1/auth/register ^
  -H "Content-Type: application/json" ^
  -d "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john@gaadix.com\",\"password\":\"password123\",\"phone\":\"9876543210\"}"
echo.
echo.

timeout /t 2 /nobreak >nul

echo 2. Testing User Login...
curl -X POST http://localhost:8081/api/v1/auth/login ^
  -H "Content-Type: application/json" ^
  -d "{\"email\":\"john@gaadix.com\",\"password\":\"password123\"}"
echo.
echo.

timeout /t 2 /nobreak >nul

echo 3. Testing Get User by Email...
curl -X GET http://localhost:8081/api/v1/users/email/john@gaadix.com ^
  -H "Content-Type: application/json"
echo.
echo.

echo ========================================
echo Tests Complete!
echo ========================================
