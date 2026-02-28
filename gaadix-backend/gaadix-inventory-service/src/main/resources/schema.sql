-- Create Inventory Database
CREATE DATABASE IF NOT EXISTS gaadix_inventory_db;

USE gaadix_inventory_db;

-- Tables will be auto-created by Hibernate
-- This script is for manual database setup if needed

-- Verify tables after running the application
SHOW TABLES;

-- Sample queries to verify data
SELECT * FROM cars;
SELECT * FROM vehicle_images;
SELECT * FROM vehicle_inspections;
