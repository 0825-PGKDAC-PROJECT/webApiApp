# GaadiX Pricing Service

Dynamic Pricing, Market Trends, and Valuation

## Features

- Dynamic price estimation based on vehicle age
- Depreciation calculation (15% per year)
- Odometer-based price adjustment
- Market trend factor (+5%)
- Price range calculation (min/max)
- Historical price tracking

## Database

- Database: `gaadix_pricing_db`
- Port: 8086
- Table: `price_estimates`

## API Endpoints

### Pricing
- `POST /api/v1/pricing/estimate` - Calculate price estimate
- `GET /api/v1/pricing/estimate/{carId}` - Get price estimate

## Pricing Algorithm

### 1. Depreciation Calculation
```
depreciationFactor = 1 - (0.15 × vehicleAge)
baseEstimate = originalPrice × depreciationFactor
```

### 2. Odometer Adjustment
- < 20,000 km: 100% (no adjustment)
- 20,000-50,000 km: 95%
- 50,000-80,000 km: 90%
- 80,000-120,000 km: 85%
- > 120,000 km: 80%

### 3. Market Trend
```
marketAdjustment = baseEstimate × 0.05
estimatedPrice = baseEstimate + marketAdjustment
```

### 4. Price Range
```
minPrice = estimatedPrice × 0.90
maxPrice = estimatedPrice × 1.10
```

## Example Calculation

**Input:**
- Original Price: ₹10,00,000
- Year: 2020 (4 years old)
- Odometer: 45,000 km

**Calculation:**
1. Depreciation: 1 - (0.15 × 4) = 0.40
2. Base: ₹10,00,000 × 0.40 = ₹4,00,000
3. Odometer: ₹4,00,000 × 0.95 = ₹3,80,000
4. Market: ₹3,80,000 × 1.05 = ₹3,99,000
5. Range: ₹3,59,100 - ₹4,38,900

## Setup

1. Create database: `CREATE DATABASE gaadix_pricing_db;`
2. Build: `mvn clean install`
3. Run: `mvn spring-boot:run`
4. Access: http://localhost:8086

## Testing

Use `pricing-service-tests.http` for API testing
