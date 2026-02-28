# GaadiX Payment Service

Transaction Processing, Payment Gateway Integration, and Refunds

## Features

- Payment initiation and processing
- Multiple payment methods (UPI, Cards, Net Banking, Wallet, EMI)
- Payment status tracking
- Refund processing
- Buyer and seller payment history
- Mock payment gateway integration

## Database

- Database: `gaadix_payment_db`
- Port: 8084
- Table: `payments`

## API Endpoints

### Payments
- `POST /api/v1/payments` - Initiate payment
- `POST /api/v1/payments/{id}/process` - Process payment
- `POST /api/v1/payments/{id}/complete` - Complete payment
- `POST /api/v1/payments/{id}/refund` - Refund payment
- `GET /api/v1/payments/{id}` - Get payment by ID
- `GET /api/v1/payments/buyer/{buyerId}` - Get buyer payments
- `GET /api/v1/payments/seller/{sellerId}` - Get seller payments

## Payment Status Flow

1. **PENDING** - Payment initiated
2. **PROCESSING** - Payment being processed by gateway
3. **COMPLETED** - Payment successful
4. **FAILED** - Payment failed
5. **REFUNDED** - Payment refunded
6. **CANCELLED** - Payment cancelled

## Payment Methods

- CREDIT_CARD
- DEBIT_CARD
- UPI
- NET_BANKING
- WALLET
- EMI

## Setup

1. Create database: `CREATE DATABASE gaadix_payment_db;`
2. Build: `mvn clean install`
3. Run: `mvn spring-boot:run`
4. Access: http://localhost:8084

## Testing

Use `payment-service-tests.http` for API testing
