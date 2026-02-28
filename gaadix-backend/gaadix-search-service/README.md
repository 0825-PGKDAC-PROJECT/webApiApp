# GaadiX Search Service

Advanced Vehicle Search with Filters and Geo-spatial Queries

## Features

- Keyword search (brand, model, description)
- Multi-filter search (price, year, fuel type, transmission, odometer)
- Location-based search (radius search using Haversine formula)
- Pagination and sorting
- Status filtering
- City and state filtering

## Database

- Uses: `gaadix_inventory_db` (shared with Inventory Service)
- Port: 8083

## API Endpoints

### Search
- `POST /api/v1/search/vehicles` - Advanced search with filters
- `GET /api/v1/search/vehicles` - Simple search with query params

## Search Filters

- **keyword** - Search in brand, model, description
- **brand** - Exact brand match
- **model** - Exact model match
- **minYear/maxYear** - Year range
- **fuelType** - PETROL, DIESEL, ELECTRIC, HYBRID
- **transmission** - MANUAL, AUTOMATIC
- **minPrice/maxPrice** - Price range
- **maxOdometer** - Maximum odometer reading
- **city/state** - Location filters
- **latitude/longitude/radiusKm** - Geo-spatial search
- **status** - Vehicle status (AVAILABLE, SOLD, etc.)
- **sortBy** - Sort field (default: createdAt)
- **sortDirection** - ASC or DESC

## Geo-spatial Search

Uses Haversine formula to calculate distance:
```
distance = 6371 * acos(cos(lat1) * cos(lat2) * cos(lon2 - lon1) + sin(lat1) * sin(lat2))
```

## Setup

1. Ensure Inventory Service database exists
2. Build: `mvn clean install`
3. Run: `mvn spring-boot:run`
4. Access: http://localhost:8083

## Testing

Use `search-service-tests.http` for API testing
