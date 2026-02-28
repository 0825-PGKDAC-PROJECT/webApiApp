# GaadiX Fraud Detection Service

Transaction Monitoring and Risk Assessment

## Features

- Real-time fraud detection
- Risk scoring (0-100)
- Multi-factor risk analysis
- Transaction pattern monitoring
- User behavior analysis
- Automated recommendations

## Database

- Database: `gaadix_fraud_db`
- Port: 8088
- Table: `fraud_checks`

## API Endpoints

### Fraud Detection
- `POST /api/v1/fraud/check` - Check transaction for fraud
- `GET /api/v1/fraud/transaction/{transactionId}` - Get fraud check result

## Risk Factors

### 1. High Transaction Amount (30 points)
- Transactions > ₹10,00,000

### 2. Multiple Transactions (20 points)
- User has > 5 transactions

### 3. Suspicious IP (10 points)
- Private/suspicious IP addresses

### 4. New User High Value (25 points)
- First transaction > ₹5,00,000

### 5. Rapid Transactions (15 points)
- > 3 transactions in short time

## Risk Levels

- **LOW** (0-29): Safe to proceed
- **MEDIUM** (30-59): Additional verification
- **HIGH** (60-79): Hold for verification
- **CRITICAL** (80-100): Block immediately

## Risk Score Calculation

```
Total Risk Score = Sum of all risk factors
Fraud Threshold = 70
```

## Recommendations

- **CRITICAL**: Block transaction, manual review
- **HIGH**: Hold transaction, contact user
- **MEDIUM**: Additional verification steps
- **LOW**: Approve transaction

## Example Scenarios

### Scenario 1: Low Risk
- Amount: ₹3,00,000
- User: Existing (2 transactions)
- Score: 0
- Level: LOW
- Action: Approve

### Scenario 2: High Risk
- Amount: ₹15,00,000
- User: New (0 transactions)
- Score: 55
- Level: MEDIUM
- Action: Verify

### Scenario 3: Critical Risk
- Amount: ₹20,00,000
- User: New with rapid transactions
- Score: 85
- Level: CRITICAL
- Action: Block

## Setup

1. Create database: `CREATE DATABASE gaadix_fraud_db;`
2. Build: `mvn clean install`
3. Run: `mvn spring-boot:run`
4. Access: http://localhost:8088

## Testing

Use `fraud-service-tests.http` for API testing
