# GaadiX API Documentation

## Base URL
```
http://localhost:8080/api/v1
```

## Authentication
Most endpoints require JWT authentication. Include the token in the Authorization header:
```
Authorization: Bearer {your_jwt_token}
```

---

## User Service APIs

### Register User
```http
POST /auth/register
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@gaadix.com",
  "password": "password123",
  "phone": "9876543210"
}
```

**Response (201 Created)**:
```json
{
  "userId": 1,
  "email": "john@gaadix.com",
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "expiresIn": 86400
}
```

### Login User
```http
POST /auth/login
Content-Type: application/json

{
  "email": "john@gaadix.com",
  "password": "password123"
}
```

**Response (200 OK)**: Same as register

### Get User by ID
```http
GET /users/{id}
Authorization: Bearer {token}
```

**Response (200 OK)**:
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@gaadix.com",
  "phone": "9876543210",
  "role": "BUYER",
  "createdAt": "2024-01-15T10:30:00"
}
```

---

## Inventory Service APIs

### Create Vehicle
```http
POST /vehicles
Authorization: Bearer {token}
Content-Type: application/json

{
  "sellerId": 1,
  "brand": "Maruti Suzuki",
  "model": "Swift VXI",
  "year": 2020,
  "price": 550000,
  "mileage": 25000,
  "fuelType": "PETROL",
  "transmission": "MANUAL",
  "color": "Red",
  "registrationNumber": "MH12AB1234",
  "description": "Well maintained Swift"
}
```

**Response (201 Created)**:
```json
{
  "id": 1,
  "sellerId": 1,
  "brand": "Maruti Suzuki",
  "model": "Swift VXI",
  "year": 2020,
  "price": 550000,
  "status": "AVAILABLE",
  "createdAt": "2024-01-15T10:30:00"
}
```

### Get All Vehicles
```http
GET /vehicles
Authorization: Bearer {token}
```

**Response (200 OK)**:
```json
[
  {
    "id": 1,
    "brand": "Maruti Suzuki",
    "model": "Swift VXI",
    "year": 2020,
    "price": 550000,
    "status": "AVAILABLE"
  }
]
```

### Get Vehicle by ID
```http
GET /vehicles/{id}
Authorization: Bearer {token}
```

**Response (200 OK)**:
```json
{
  "id": 1,
  "sellerId": 1,
  "brand": "Maruti Suzuki",
  "model": "Swift VXI",
  "year": 2020,
  "price": 550000,
  "mileage": 25000,
  "fuelType": "PETROL",
  "transmission": "MANUAL",
  "color": "Red",
  "registrationNumber": "MH12AB1234",
  "status": "AVAILABLE",
  "description": "Well maintained Swift",
  "images": [
    {
      "id": 1,
      "imageUrl": "https://images.gaadix.com/swift-front.jpg",
      "isPrimary": true
    }
  ],
  "inspection": {
    "id": 1,
    "overallScore": 8.6,
    "engineScore": 9,
    "transmissionScore": 9,
    "remarks": "Excellent condition"
  }
}
```

### Upload Vehicle Image
```http
POST /images
Authorization: Bearer {token}
Content-Type: application/json

{
  "vehicleId": 1,
  "imageUrl": "https://images.gaadix.com/swift-front.jpg",
  "isPrimary": true
}
```

### Add Inspection Report
```http
POST /inspections
Authorization: Bearer {token}
Content-Type: application/json

{
  "vehicleId": 1,
  "inspectorName": "Inspector A",
  "engineScore": 9,
  "transmissionScore": 9,
  "suspensionScore": 8,
  "brakesScore": 9,
  "electricalScore": 9,
  "bodyScore": 8,
  "interiorScore": 9,
  "tyresScore": 8,
  "remarks": "Excellent condition"
}
```

---

## Search Service APIs

### Search Vehicles
```http
GET /search/vehicles?brand=Maruti&minPrice=300000&maxPrice=800000&year=2020&fuelType=PETROL
```

**Query Parameters**:
- brand (optional)
- model (optional)
- minPrice (optional)
- maxPrice (optional)
- minYear (optional)
- maxYear (optional)
- fuelType (optional): PETROL, DIESEL, ELECTRIC, CNG
- transmission (optional): MANUAL, AUTOMATIC
- color (optional)
- minMileage (optional)
- maxMileage (optional)
- status (optional): AVAILABLE, SOLD, RESERVED

**Response (200 OK)**:
```json
[
  {
    "id": 1,
    "brand": "Maruti Suzuki",
    "model": "Swift VXI",
    "year": 2020,
    "price": 550000,
    "mileage": 25000,
    "fuelType": "PETROL",
    "transmission": "MANUAL",
    "status": "AVAILABLE"
  }
]
```

### Search by Location
```http
GET /search/vehicles/nearby?latitude=19.0760&longitude=72.8777&radius=50
```

**Query Parameters**:
- latitude (required)
- longitude (required)
- radius (required): in kilometers

**Response (200 OK)**:
```json
[
  {
    "id": 1,
    "brand": "Maruti Suzuki",
    "model": "Swift VXI",
    "distance": 12.5,
    "price": 550000
  }
]
```

---

## Payment Service APIs

### Create Payment
```http
POST /payments
Authorization: Bearer {token}
Content-Type: application/json

{
  "vehicleId": 1,
  "buyerId": 1,
  "amount": 550000,
  "paymentMethod": "UPI"
}
```

**Payment Methods**: UPI, CREDIT_CARD, DEBIT_CARD, NET_BANKING, BANK_TRANSFER, CASH

**Response (201 Created)**:
```json
{
  "id": 1,
  "vehicleId": 1,
  "buyerId": 1,
  "amount": 550000,
  "paymentMethod": "UPI",
  "status": "PENDING",
  "transactionId": "TXN1234567890",
  "createdAt": "2024-01-15T10:30:00"
}
```

### Get Payment by ID
```http
GET /payments/{id}
Authorization: Bearer {token}
```

### Process Payment
```http
PUT /payments/{id}/process
Authorization: Bearer {token}
```

### Complete Payment
```http
PUT /payments/{id}/complete
Authorization: Bearer {token}
```

### Initiate Refund
```http
POST /payments/{id}/refund
Authorization: Bearer {token}
Content-Type: application/json

{
  "reason": "Buyer cancelled"
}
```

---

## RTO Service APIs

### Verify RTO
```http
POST /rto/verify
Authorization: Bearer {token}
Content-Type: application/json

{
  "registrationNumber": "MH12AB1234",
  "vehicleId": 1
}
```

**Response (200 OK)**:
```json
{
  "id": 1,
  "vehicleId": 1,
  "registrationNumber": "MH12AB1234",
  "ownerName": "Rajesh Kumar",
  "registrationDate": "2020-03-15",
  "state": "Maharashtra",
  "rtoCode": "MH12",
  "status": "VERIFIED",
  "verifiedAt": "2024-01-15T10:30:00"
}
```

### Get Verification by ID
```http
GET /rto/verification/{id}
Authorization: Bearer {token}
```

---

## Pricing Service APIs

### Get Pricing Estimate
```http
GET /pricing/estimate?vehicleId=1
```

**Response (200 OK)**:
```json
{
  "vehicleId": 1,
  "originalPrice": 550000,
  "estimatedPrice": 495000,
  "minPrice": 445500,
  "maxPrice": 544500,
  "depreciationRate": 15,
  "ageInYears": 4,
  "mileageAdjustment": -10,
  "calculatedAt": "2024-01-15T10:30:00"
}
```

---

## Notification Service APIs

### Send Notification
```http
POST /notifications/send
Authorization: Bearer {token}
Content-Type: application/json

{
  "userId": 1,
  "type": "EMAIL",
  "notificationType": "PAYMENT_SUCCESS",
  "subject": "Payment Successful",
  "message": "Your payment of ₹550000 was successful"
}
```

**Notification Types**: EMAIL, SMS, PUSH

**Notification Categories**: 
- PAYMENT_SUCCESS
- PAYMENT_FAILED
- VEHICLE_SOLD
- VEHICLE_LISTED
- INSPECTION_SCHEDULED
- RTO_VERIFIED
- FRAUD_ALERT

**Response (200 OK)**:
```json
{
  "id": 1,
  "userId": 1,
  "type": "EMAIL",
  "status": "SENT",
  "sentAt": "2024-01-15T10:30:00"
}
```

### Get User Notifications
```http
GET /notifications/user/{userId}
Authorization: Bearer {token}
```

---

## Fraud Detection Service APIs

### Check Fraud Risk
```http
POST /fraud/check
Authorization: Bearer {token}
Content-Type: application/json

{
  "vehicleId": 1,
  "sellerId": 2,
  "buyerId": 1,
  "transactionAmount": 550000
}
```

**Response (200 OK)**:
```json
{
  "id": 1,
  "vehicleId": 1,
  "riskScore": 25,
  "riskLevel": "LOW",
  "recommendation": "APPROVE",
  "factors": {
    "priceDeviation": 5,
    "sellerHistory": 0,
    "buyerHistory": 0,
    "vehicleAge": 10,
    "transactionPattern": 10
  },
  "checkedAt": "2024-01-15T10:30:00"
}
```

**Risk Levels**: LOW, MEDIUM, HIGH, CRITICAL

**Recommendations**: APPROVE, REVIEW, REJECT

### Get Fraud Check by ID
```http
GET /fraud/check/{id}
Authorization: Bearer {token}
```

---

## Analytics Service APIs

### Get Dashboard Stats
```http
GET /analytics/dashboard
Authorization: Bearer {token}
```

**Response (200 OK)**:
```json
{
  "totalUsers": 1250,
  "totalVehicles": 450,
  "totalTransactions": 320,
  "totalRevenue": 145000000,
  "activeListings": 180,
  "pendingVerifications": 25
}
```

### Get Sales Analytics
```http
GET /analytics/sales
Authorization: Bearer {token}
```

**Response (200 OK)**:
```json
{
  "monthlySales": {
    "2024-01": 25000000,
    "2024-02": 28000000,
    "2024-03": 32000000
  },
  "averagePrice": 625000,
  "totalSales": 145000000,
  "growthRate": 12.5
}
```

### Get Popular Brands
```http
GET /analytics/popular-brands
Authorization: Bearer {token}
```

**Response (200 OK)**:
```json
{
  "brandCounts": {
    "Maruti Suzuki": 120,
    "Hyundai": 85,
    "Honda": 65,
    "Tata": 55,
    "Mahindra": 45
  },
  "topBrand": "Maruti Suzuki",
  "totalBrands": 15
}
```

---

## Error Responses

### 400 Bad Request
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/v1/vehicles"
}
```

### 401 Unauthorized
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid or expired token"
}
```

### 404 Not Found
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Vehicle not found with id: 999"
}
```

### 500 Internal Server Error
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "An unexpected error occurred"
}
```

---

## Rate Limiting
- **Limit**: 100 requests per minute per IP
- **Header**: X-RateLimit-Remaining

## Pagination
For list endpoints, use query parameters:
- `page`: Page number (default: 0)
- `size`: Page size (default: 20)
- `sort`: Sort field and direction (e.g., `price,desc`)

Example:
```http
GET /vehicles?page=0&size=10&sort=price,desc
```
