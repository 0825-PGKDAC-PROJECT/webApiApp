# Testing Inventory Service in STS 4.3

## Prerequisites
- User Service running on port 8081
- MySQL running with credentials configured

## Step 1: Create Database

```sql
CREATE DATABASE IF NOT EXISTS gaadix_inventory_db;
```

## Step 2: Build Inventory Service

1. Right-click `gaadix-inventory-service`
2. Run As → Maven install
3. Wait for BUILD SUCCESS

## Step 3: Run Inventory Service

1. Navigate to: `src/main/java/com/gaadix/inventory/InventoryServiceApplication.java`
2. Right-click → Run As → Spring Boot App
3. Verify console shows: "Started InventoryServiceApplication"
4. Service runs on port 8082

## Step 4: Test APIs

Use `inventory-service-tests.http` file

### Test Sequence:

1. **Create Vehicle** (POST /api/v1/vehicles)
2. **Get Vehicle** (GET /api/v1/vehicles/1)
3. **Add Images** (POST /api/v1/vehicles/1/images)
4. **Create Inspection** (POST /api/v1/vehicles/1/inspections)
5. **Publish Vehicle** (POST /api/v1/vehicles/1/publish)
6. **Get All Vehicles** (GET /api/v1/vehicles?status=AVAILABLE)

## Step 5: Verify Database

```sql
USE gaadix_inventory_db;
SHOW TABLES;
-- Expected: cars, vehicle_images, vehicle_inspections

SELECT * FROM cars;
SELECT * FROM vehicle_images;
SELECT * FROM vehicle_inspections;
```

## Verification Checklist

- [ ] Database gaadix_inventory_db created
- [ ] Service starts on port 8082
- [ ] Vehicle creation returns 201
- [ ] Images can be added
- [ ] Inspections can be created
- [ ] Vehicle can be published (status changes to AVAILABLE)
- [ ] All 3 tables populated in database
