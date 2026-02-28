# GaadiX Common Module

Shared components used across all microservices.

## Structure

### Entities (7)
- `BaseEntity` - Base class with id, createdAt, updatedAt
- `User` - User entity with roles and authentication fields
- `Car` - Vehicle entity with all 11 fuel types support
- `RTOVerification` - Mock RTO verification data
- `Transaction` - Payment transaction records
- `FraudScore` - Fraud detection scores
- `PriceHistory` - Price change tracking

### Enums (7)
- `FuelType` - 11 fuel types (Petrol, Diesel, CNG, LPG, Electric, Hybrid, PHEV, Hydrogen, Ethanol, Bio-CNG, Flex Fuel)
- `CarStatus` - Vehicle listing status
- `Transmission` - Transmission types
- `OwnershipType` - Owner count
- `VerificationStatus` - RTO verification status
- `UserRole` - User roles (Admin, Seller, Buyer, Dealer, Inspector)
- `TransactionStatus` - Payment status

### DTOs (3)
- `ApiResponse<T>` - Standard API response wrapper
- `UserDTO` - User data transfer object
- `CarDTO` - Car data transfer object

### Exceptions (3)
- `ResourceNotFoundException`
- `BadRequestException`
- `UnauthorizedException`

### Utilities (3)
- `AppConstants` - Application-wide constants
- `DateTimeUtil` - Date/time formatting utilities
- `ValidationUtil` - Common validation methods

### Configuration (2)
- `JpaConfig` - JPA auditing configuration
- `ModelMapperConfig` - ModelMapper bean configuration

## Usage

All microservices depend on this module:

```xml
<dependency>
    <groupId>com.gaadix</groupId>
    <artifactId>gaadix-common</artifactId>
    <version>${project.version}</version>
</dependency>
```

## Features

- ✅ JPA Auditing enabled (auto createdAt/updatedAt)
- ✅ Lombok for boilerplate reduction
- ✅ ModelMapper for DTO conversions
- ✅ Validation annotations
- ✅ All 11 fuel types supported
- ✅ Standardized API responses
