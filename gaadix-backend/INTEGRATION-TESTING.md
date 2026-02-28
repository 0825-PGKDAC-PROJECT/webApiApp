# GaadiX Integration Testing Guide

## Overview
This guide covers integration testing for GaadiX microservices, focusing on inter-service communication and end-to-end workflows.

## Test Scenarios

### Scenario 1: Complete Vehicle Purchase Flow

**Objective**: Test the entire purchase workflow from search to payment

**Services Involved**: Search → Inventory → Pricing → Fraud → RTO → Payment → Notification

**Steps**:

1. **Register Buyer**
```http
POST http://localhost:8080/api/v1/auth/register
Content-Type: application/json

{
  "firstName": "Test",
  "lastName": "Buyer",
  "email": "buyer@test.com",
  "password": "test123",
  "phone": "9999999999"
}
```
**Expected**: 201 Created, JWT token returned

2. **Search for Vehicles**
```http
GET http://localhost:8080/api/v1/search/vehicles?brand=Maruti&minPrice=400000&maxPrice=600000
```
**Expected**: 200 OK, list of matching vehicles

3. **Get Vehicle Details**
```http
GET http://localhost:8080/api/v1/vehicles/1
Authorization: Bearer {token}
```
**Expected**: 200 OK, vehicle with images and inspection

4. **Get Pricing Estimate**
```http
GET http://localhost:8080/api/v1/pricing/estimate?vehicleId=1
```
**Expected**: 200 OK, price range and estimated value

5. **Check Fraud Risk**
```http
POST http://localhost:8080/api/v1/fraud/check
Authorization: Bearer {token}
Content-Type: application/json

{
  "vehicleId": 1,
  "sellerId": 1,
  "buyerId": 2,
  "transactionAmount": 550000
}
```
**Expected**: 200 OK, risk score and recommendation

6. **Verify RTO**
```http
POST http://localhost:8080/api/v1/rto/verify
Authorization: Bearer {token}
Content-Type: application/json

{
  "registrationNumber": "MH12AB1234",
  "vehicleId": 1
}
```
**Expected**: 200 OK, verification status

7. **Create Payment**
```http
POST http://localhost:8080/api/v1/payments
Authorization: Bearer {token}
Content-Type: application/json

{
  "vehicleId": 1,
  "buyerId": 2,
  "amount": 550000,
  "paymentMethod": "UPI"
}
```
**Expected**: 201 Created, payment ID returned

8. **Verify Notification Sent**
```http
GET http://localhost:8080/api/v1/notifications/user/2
Authorization: Bearer {token}
```
**Expected**: 200 OK, payment notification present

**Success Criteria**:
- All API calls return expected status codes
- Data flows correctly between services
- Payment status is PENDING
- Notification is sent to buyer
- Analytics event is tracked

---

### Scenario 2: Vehicle Listing by Seller

**Objective**: Test seller listing a vehicle with images and inspection

**Services Involved**: User → Inventory → Pricing → Search → Notification

**Steps**:

1. **Register Seller**
```http
POST http://localhost:8080/api/v1/auth/register
Content-Type: application/json

{
  "firstName": "Test",
  "lastName": "Seller",
  "email": "seller@test.com",
  "password": "test123",
  "phone": "8888888888"
}
```

2. **Create Vehicle Listing**
```http
POST http://localhost:8080/api/v1/vehicles
Authorization: Bearer {token}
Content-Type: application/json

{
  "sellerId": 3,
  "brand": "Honda",
  "model": "Civic",
  "year": 2021,
  "price": 1500000,
  "mileage": 15000,
  "fuelType": "PETROL",
  "transmission": "AUTOMATIC",
  "color": "White",
  "registrationNumber": "MH01XY9999",
  "description": "Excellent condition"
}
```
**Expected**: 201 Created, vehicle ID returned

3. **Upload Vehicle Images**
```http
POST http://localhost:8080/api/v1/images
Authorization: Bearer {token}
Content-Type: application/json

{
  "vehicleId": 6,
  "imageUrl": "https://images.gaadix.com/civic-front.jpg",
  "isPrimary": true
}
```
**Expected**: 201 Created

4. **Add Inspection Report**
```http
POST http://localhost:8080/api/v1/inspections
Authorization: Bearer {token}
Content-Type: application/json

{
  "vehicleId": 6,
  "inspectorName": "Inspector A",
  "engineScore": 9,
  "transmissionScore": 9,
  "suspensionScore": 8,
  "brakesScore": 9,
  "electricalScore": 9,
  "bodyScore": 9,
  "interiorScore": 9,
  "tyresScore": 8,
  "remarks": "Excellent condition"
}
```
**Expected**: 201 Created

5. **Verify Vehicle in Search**
```http
GET http://localhost:8080/api/v1/search/vehicles?brand=Honda
```
**Expected**: 200 OK, newly listed vehicle appears

6. **Get Pricing Estimate**
```http
GET http://localhost:8080/api/v1/pricing/estimate?vehicleId=6
```
**Expected**: 200 OK, pricing calculated

**Success Criteria**:
- Vehicle created successfully
- Images uploaded
- Inspection report added
- Vehicle appears in search results
- Pricing estimate generated

---

### Scenario 3: Payment Processing and Refund

**Objective**: Test payment lifecycle including refund

**Services Involved**: Payment → Notification → Analytics

**Steps**:

1. **Create Payment**
```http
POST http://localhost:8080/api/v1/payments
Authorization: Bearer {token}
Content-Type: application/json

{
  "vehicleId": 1,
  "buyerId": 2,
  "amount": 550000,
  "paymentMethod": "CREDIT_CARD"
}
```

2. **Process Payment**
```http
PUT http://localhost:8080/api/v1/payments/1/process
Authorization: Bearer {token}
```
**Expected**: 200 OK, status changed to PROCESSING

3. **Complete Payment**
```http
PUT http://localhost:8080/api/v1/payments/1/complete
Authorization: Bearer {token}
```
**Expected**: 200 OK, status changed to COMPLETED

4. **Initiate Refund**
```http
POST http://localhost:8080/api/v1/payments/1/refund
Authorization: Bearer {token}
Content-Type: application/json

{
  "reason": "Buyer cancelled"
}
```
**Expected**: 200 OK, refund initiated

5. **Verify Notifications**
```http
GET http://localhost:8080/api/v1/notifications/user/2
Authorization: Bearer {token}
```
**Expected**: Multiple notifications (payment success, refund initiated)

**Success Criteria**:
- Payment transitions through all states
- Refund processed successfully
- Notifications sent at each stage
- Analytics events tracked

---

### Scenario 4: Fraud Detection Integration

**Objective**: Test fraud detection with high-risk transaction

**Services Involved**: Fraud → Payment → Notification

**Steps**:

1. **Check High-Risk Transaction**
```http
POST http://localhost:8080/api/v1/fraud/check
Authorization: Bearer {token}
Content-Type: application/json

{
  "vehicleId": 1,
  "sellerId": 1,
  "buyerId": 2,
  "transactionAmount": 5000000
}
```
**Expected**: 200 OK, HIGH or CRITICAL risk level

2. **Attempt Payment (Should be Blocked)**
```http
POST http://localhost:8080/api/v1/payments
Authorization: Bearer {token}
Content-Type: application/json

{
  "vehicleId": 1,
  "buyerId": 2,
  "amount": 5000000,
  "paymentMethod": "CASH"
}
```
**Expected**: 400 Bad Request or payment requires manual review

**Success Criteria**:
- Fraud detection identifies high-risk transaction
- Payment is blocked or flagged for review
- Admin notification sent

---

### Scenario 5: Location-Based Search

**Objective**: Test geo-spatial search functionality

**Services Involved**: Search → Inventory

**Steps**:

1. **Search Vehicles Near Mumbai**
```http
GET http://localhost:8080/api/v1/search/vehicles/nearby?latitude=19.0760&longitude=72.8777&radius=50
```
**Expected**: 200 OK, vehicles within 50km

2. **Search with Filters**
```http
GET http://localhost:8080/api/v1/search/vehicles/nearby?latitude=19.0760&longitude=72.8777&radius=100&brand=Maruti&maxPrice=600000
```
**Expected**: 200 OK, filtered results

**Success Criteria**:
- Haversine formula calculates distance correctly
- Results sorted by distance
- Filters applied correctly

---

## Automated Integration Tests

### JUnit Test Example

```java
@SpringBootTest
@AutoConfigureMockMvc
public class VehiclePurchaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCompletePurchaseFlow() throws Exception {
        // 1. Register user
        String registerResponse = mockMvc.perform(post("/api/v1/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"email\":\"test@test.com\",\"password\":\"test123\"}"))
            .andExpect(status().isCreated())
            .andReturn().getResponse().getContentAsString();
        
        String token = extractToken(registerResponse);

        // 2. Search vehicles
        mockMvc.perform(get("/api/v1/search/vehicles?brand=Maruti")
            .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].brand").value("Maruti"));

        // 3. Create payment
        mockMvc.perform(post("/api/v1/payments")
            .header("Authorization", "Bearer " + token)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"vehicleId\":1,\"amount\":550000}"))
            .andExpect(status().isCreated());
    }
}
```

---

## Performance Testing

### Load Test Scenarios

**Tool**: Apache JMeter or Gatling

**Scenario 1: Search Load Test**
- Concurrent Users: 100
- Ramp-up Time: 10 seconds
- Duration: 5 minutes
- Expected TPS: > 50

**Scenario 2: Payment Processing**
- Concurrent Users: 50
- Ramp-up Time: 5 seconds
- Duration: 3 minutes
- Expected Response Time: < 2 seconds

---

## Test Data Setup

### Prerequisites
```bash
# Run database creation
mysql -u root -p < database-scripts/00-create-databases.sql

# Load sample data
mysql -u KD2-GANESH-92393 -p < database-scripts/01-sample-data.sql
```

### Test Users
- **Buyer**: buyer@test.com / test123
- **Seller**: seller@test.com / test123
- **Admin**: admin@gaadix.com / password123

---

## Validation Checklist

- [ ] All services start successfully
- [ ] API Gateway routes requests correctly
- [ ] JWT authentication works across services
- [ ] Database transactions are atomic
- [ ] Error handling is consistent
- [ ] Notifications are sent correctly
- [ ] Analytics events are tracked
- [ ] Fraud detection flags high-risk transactions
- [ ] Payment lifecycle works end-to-end
- [ ] Search returns accurate results
- [ ] RTO verification completes
- [ ] Pricing calculations are correct

---

## Troubleshooting

### Common Issues

**Issue**: Service communication timeout
**Solution**: Check if all services are running, verify network connectivity

**Issue**: JWT token invalid
**Solution**: Ensure JWT secret matches across services

**Issue**: Database connection failed
**Solution**: Verify MySQL is running, check credentials

**Issue**: CORS error
**Solution**: Check CORS configuration in API Gateway
