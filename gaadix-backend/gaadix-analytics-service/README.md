# GaadiX Analytics Service

Business Intelligence and Reporting

## Features

- Dashboard statistics
- Sales analytics with monthly trends
- Popular brands analysis
- Event tracking
- Revenue metrics
- User activity monitoring

## Database

- Database: `gaadix_analytics_db`
- Port: 8089
- Table: `analytics_events`

## API Endpoints

### Analytics
- `GET /api/v1/analytics/dashboard` - Get dashboard statistics
- `GET /api/v1/analytics/sales` - Get sales analytics
- `GET /api/v1/analytics/popular-brands` - Get popular brands

## Dashboard Metrics

- Total Users
- Total Vehicles
- Total Transactions
- Total Revenue
- Active Listings
- Pending Verifications

## Sales Analytics

- Monthly sales trends (6 months)
- Average vehicle price
- Total sales count
- Growth rate percentage

## Popular Brands

- Brand-wise vehicle counts
- Top performing brand
- Total brands available

## Event Tracking

Track various events:
- User registrations
- Vehicle listings
- Transactions
- Searches
- Views

## Mock Data

Currently returns mock data for:
- 1,250 total users
- 850 total vehicles
- 320 transactions
- ₹4.5 Cr total revenue
- Top 5 brands with counts

## Integration Points

Can be integrated with:
- Google Analytics
- Mixpanel
- Custom BI tools
- Data warehouses

## Setup

1. Create database: `CREATE DATABASE gaadix_analytics_db;`
2. Build: `mvn clean install`
3. Run: `mvn spring-boot:run`
4. Access: http://localhost:8089

## Testing

Use `analytics-service-tests.http` for API testing
