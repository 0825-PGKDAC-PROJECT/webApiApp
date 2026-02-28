# GaadiX RTO Service

Mock RTO Verification and Vehicle Compliance

## Features

- Vehicle registration verification
- RTO code validation
- Mock RTO database lookup
- Compliance checking (fitness, insurance, PUC)
- Blacklist verification
- NOC status checking
- State-wise RTO mapping

## Database

- Database: `gaadix_rto_db`
- Port: 8085
- Table: `rto_verifications`

## API Endpoints

### Verification
- `POST /api/v1/rto/verify` - Verify vehicle registration
- `GET /api/v1/rto/verify/{registrationNumber}` - Get verification details

## Registration Number Format

Valid format: `XX##XX####`
- XX: State code (2 letters)
- ##: RTO district code (1-2 digits)
- XX: Series (1-2 letters)
- ####: Number (4 digits)

Examples:
- MH12AB1234 (Maharashtra)
- DL01CA5678 (Delhi)
- KA05MN9876 (Karnataka)

## Verification Status

- PENDING - Verification initiated
- VERIFIED - Successfully verified
- FAILED - Verification failed
- REJECTED - Blacklisted or invalid

## Mock Verification Logic

- Validates registration number format
- Extracts state from RTO code
- Generates mock owner details
- Sets compliance dates (fitness, insurance, PUC)
- Checks blacklist status
- Tracks verification attempts

## State Codes

- MH - Maharashtra
- DL - Delhi
- KA - Karnataka
- TN - Tamil Nadu
- GJ - Gujarat

## Setup

1. Create database: `CREATE DATABASE gaadix_rto_db;`
2. Build: `mvn clean install`
3. Run: `mvn spring-boot:run`
4. Access: http://localhost:8085

## Testing

Use `rto-service-tests.http` for API testing
