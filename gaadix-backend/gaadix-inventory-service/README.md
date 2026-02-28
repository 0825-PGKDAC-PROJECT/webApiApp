# GaadiX Inventory Service

Vehicle Management, Images, and Inspections Service

## Features

- Vehicle CRUD operations
- Vehicle image management (multiple images per vehicle)
- Vehicle inspection records with scoring
- Seller-specific vehicle listings
- Vehicle status management (DRAFT, AVAILABLE, SOLD, etc.)
- Support for all fuel types (Petrol, Diesel, Electric, Hybrid)

## Database

- Database: `gaadix_inventory_db`
- Port: 8082
- Tables: `cars`, `vehicle_images`, `vehicle_inspections`

## API Endpoints

### Vehicles
- `POST /api/v1/vehicles` - Create vehicle
- `GET /api/v1/vehicles/{id}` - Get vehicle by ID
- `GET /api/v1/vehicles` - Get all vehicles (with filters)
- `GET /api/v1/vehicles/seller/{sellerId}` - Get seller vehicles
- `PUT /api/v1/vehicles/{id}` - Update vehicle
- `DELETE /api/v1/vehicles/{id}` - Delete vehicle
- `POST /api/v1/vehicles/{id}/publish` - Publish vehicle

### Images
- `POST /api/v1/vehicles/{carId}/images` - Add image
- `GET /api/v1/vehicles/{carId}/images` - Get all images
- `DELETE /api/v1/vehicles/{carId}/images/{imageId}` - Delete image

### Inspections
- `POST /api/v1/vehicles/{carId}/inspections` - Create inspection
- `GET /api/v1/vehicles/{carId}/inspections` - Get all inspections
- `GET /api/v1/vehicles/{carId}/inspections/latest` - Get latest inspection

## Setup

1. Create database: `CREATE DATABASE gaadix_inventory_db;`
2. Build: `mvn clean install`
3. Run: `mvn spring-boot:run`
4. Access: http://localhost:8082

## Testing

Use `inventory-service-tests.http` for API testing
