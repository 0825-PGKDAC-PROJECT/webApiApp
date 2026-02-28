@echo off
echo ========================================
echo GaadiX Quick Start Guide
echo ========================================
echo.

echo Step 1: Creating Database...
mysql -u KD2-GANESH-92393 -pmanager -e "CREATE DATABASE IF NOT EXISTS gaadix_user_db;"
if %errorlevel% neq 0 (
    echo ERROR: Failed to create database. Is MySQL running?
    pause
    exit /b 1
)
echo Database created successfully!
echo.

echo Step 2: Building Common Module...
cd gaadix-common
call mvn clean install -DskipTests
if %errorlevel% neq 0 (
    echo ERROR: Failed to build common module
    cd ..
    pause
    exit /b 1
)
cd ..
echo Common module built successfully!
echo.

echo Step 3: Building User Service...
cd gaadix-user-service
call mvn clean install -DskipTests
if %errorlevel% neq 0 (
    echo ERROR: Failed to build user service
    cd ..
    pause
    exit /b 1
)
echo User service built successfully!
echo.

echo ========================================
echo Build Complete!
echo ========================================
echo.
echo To start User Service:
echo   cd gaadix-user-service
echo   mvn spring-boot:run
echo.
echo Then test with:
echo   test-user-service.bat
echo.
echo Or access Swagger UI:
echo   http://localhost:8081/swagger-ui/index.html
echo.
pause
