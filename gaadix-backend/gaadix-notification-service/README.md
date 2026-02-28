# GaadiX Notification Service

Email, SMS, and Push Notifications

## Features

- Multi-channel notifications (Email, SMS, Push)
- Notification history tracking
- Status management (PENDING, SENT, FAILED, RETRY)
- Retry mechanism
- User notification history
- Mock notification providers

## Database

- Database: `gaadix_notification_db`
- Port: 8087
- Table: `notifications`

## API Endpoints

### Notifications
- `POST /api/v1/notifications` - Send notification
- `GET /api/v1/notifications/user/{userId}` - Get user notifications
- `GET /api/v1/notifications/{id}` - Get notification by ID

## Notification Types

- REGISTRATION - User registration confirmation
- LOGIN - Login alerts
- PAYMENT - Payment confirmations
- BOOKING - Booking confirmations
- VERIFICATION - Verification codes
- ALERT - Important alerts
- PROMOTIONAL - Marketing messages

## Notification Channels

- EMAIL - Email notifications
- SMS - SMS notifications
- PUSH - Push notifications

## Notification Status

- PENDING - Queued for sending
- SENT - Successfully sent
- FAILED - Failed to send
- RETRY - Queued for retry

## Mock Providers

Currently using mock providers for:
- Email (logs to console)
- SMS (logs to console)
- Push (logs to console)

## Integration Points

Can be integrated with:
- SendGrid/AWS SES for Email
- Twilio/AWS SNS for SMS
- Firebase/OneSignal for Push

## Setup

1. Create database: `CREATE DATABASE gaadix_notification_db;`
2. Build: `mvn clean install`
3. Run: `mvn spring-boot:run`
4. Access: http://localhost:8087

## Testing

Use `notification-service-tests.http` for API testing
