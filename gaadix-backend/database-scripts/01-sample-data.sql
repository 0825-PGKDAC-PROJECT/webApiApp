-- ============================================
-- GaadiX Sample Data Script
-- Inserts test data for all microservices
-- Run after services have created tables
-- ============================================

-- ============================================
-- USER SERVICE DATA
-- ============================================
USE gaadix_user_db;

INSERT INTO users (first_name, last_name, email, password, phone, role, created_at, updated_at) VALUES
('Rajesh', 'Kumar', 'rajesh@gaadix.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '9876543210', 'SELLER', NOW(), NOW()),
('Priya', 'Sharma', 'priya@gaadix.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '9876543211', 'BUYER', NOW(), NOW()),
('Amit', 'Patel', 'amit@gaadix.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '9876543212', 'SELLER', NOW(), NOW()),
('Sneha', 'Reddy', 'sneha@gaadix.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '9876543213', 'BUYER', NOW(), NOW()),
('Admin', 'User', 'admin@gaadix.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '9876543214', 'ADMIN', NOW(), NOW());

-- ============================================
-- INVENTORY SERVICE DATA
-- ============================================
USE gaadix_inventory_db;

INSERT INTO vehicles (seller_id, brand, model, year, price, mileage, fuel_type, transmission, color, registration_number, status, description, created_at, updated_at) VALUES
(1, 'Maruti Suzuki', 'Swift VXI', 2020, 550000, 25000, 'PETROL', 'MANUAL', 'Red', 'MH12AB1234', 'AVAILABLE', 'Well maintained Swift in excellent condition', NOW(), NOW()),
(1, 'Hyundai', 'Creta SX', 2021, 1250000, 18000, 'DIESEL', 'AUTOMATIC', 'White', 'MH14CD5678', 'AVAILABLE', 'Top variant Creta with all features', NOW(), NOW()),
(3, 'Honda', 'City VX', 2019, 850000, 35000, 'PETROL', 'MANUAL', 'Silver', 'DL08EF9012', 'AVAILABLE', 'Honda City in pristine condition', NOW(), NOW()),
(3, 'Tata', 'Nexon XZ+', 2022, 950000, 12000, 'DIESEL', 'AUTOMATIC', 'Blue', 'KA05GH3456', 'AVAILABLE', 'Almost new Nexon with warranty', NOW(), NOW()),
(1, 'Mahindra', 'Scorpio S11', 2018, 1100000, 45000, 'DIESEL', 'MANUAL', 'Black', 'RJ14IJ7890', 'SOLD', 'Powerful SUV for family', NOW(), NOW());

INSERT INTO vehicle_images (vehicle_id, image_url, is_primary, created_at) VALUES
(1, 'https://images.gaadix.com/swift-front.jpg', true, NOW()),
(1, 'https://images.gaadix.com/swift-side.jpg', false, NOW()),
(2, 'https://images.gaadix.com/creta-front.jpg', true, NOW()),
(3, 'https://images.gaadix.com/city-front.jpg', true, NOW()),
(4, 'https://images.gaadix.com/nexon-front.jpg', true, NOW());

INSERT INTO vehicle_inspections (vehicle_id, inspector_name, engine_score, transmission_score, suspension_score, brakes_score, electrical_score, body_score, interior_score, tyres_score, overall_score, remarks, inspected_at, created_at) VALUES
(1, 'Inspection Team A', 9, 9, 8, 9, 9, 8, 9, 8, 8.6, 'Excellent condition, minor scratches on body', NOW(), NOW()),
(2, 'Inspection Team B', 10, 10, 9, 10, 10, 9, 10, 9, 9.6, 'Like new condition, highly recommended', NOW(), NOW()),
(3, 'Inspection Team A', 8, 8, 7, 8, 8, 7, 8, 7, 7.6, 'Good condition, regular maintenance done', NOW(), NOW()),
(4, 'Inspection Team C', 10, 10, 10, 10, 10, 10, 10, 10, 10.0, 'Perfect condition with full warranty', NOW(), NOW());

-- ============================================
-- PAYMENT SERVICE DATA
-- ============================================
USE gaadix_payment_db;

INSERT INTO payments (vehicle_id, buyer_id, seller_id, amount, payment_method, status, transaction_id, created_at, updated_at) VALUES
(5, 2, 1, 1100000, 'BANK_TRANSFER', 'COMPLETED', 'TXN1234567890', NOW(), NOW()),
(1, 2, 1, 550000, 'UPI', 'PENDING', 'TXN1234567891', NOW(), NOW()),
(2, 4, 1, 1250000, 'CREDIT_CARD', 'PROCESSING', 'TXN1234567892', NOW(), NOW());

-- ============================================
-- RTO SERVICE DATA
-- ============================================
USE gaadix_rto_db;

INSERT INTO rto_verifications (vehicle_id, registration_number, owner_name, registration_date, state, rto_code, status, verified_at, created_at) VALUES
(1, 'MH12AB1234', 'Rajesh Kumar', '2020-03-15', 'Maharashtra', 'MH12', 'VERIFIED', NOW(), NOW()),
(2, 'MH14CD5678', 'Rajesh Kumar', '2021-06-20', 'Maharashtra', 'MH14', 'VERIFIED', NOW(), NOW()),
(3, 'DL08EF9012', 'Amit Patel', '2019-08-10', 'Delhi', 'DL08', 'VERIFIED', NOW(), NOW()),
(4, 'KA05GH3456', 'Amit Patel', '2022-01-25', 'Karnataka', 'KA05', 'VERIFIED', NOW(), NOW());

-- ============================================
-- NOTIFICATION SERVICE DATA
-- ============================================
USE gaadix_notification_db;

INSERT INTO notifications (user_id, type, notification_type, subject, message, status, sent_at, created_at) VALUES
(2, 'EMAIL', 'PAYMENT_SUCCESS', 'Payment Successful', 'Your payment of ₹1100000 was successful', 'SENT', NOW(), NOW()),
(1, 'SMS', 'VEHICLE_SOLD', 'Vehicle Sold', 'Your vehicle Scorpio S11 has been sold', 'SENT', NOW(), NOW()),
(4, 'PUSH', 'PAYMENT_PENDING', 'Payment Pending', 'Complete your payment for Creta SX', 'SENT', NOW(), NOW());

-- ============================================
-- FRAUD DETECTION SERVICE DATA
-- ============================================
USE gaadix_fraud_db;

INSERT INTO fraud_checks (vehicle_id, seller_id, buyer_id, transaction_amount, risk_score, risk_level, recommendation, checked_at, created_at) VALUES
(5, 1, 2, 1100000, 25, 'LOW', 'APPROVE', NOW(), NOW()),
(1, 1, 2, 550000, 30, 'LOW', 'APPROVE', NOW(), NOW()),
(2, 1, 4, 1250000, 45, 'MEDIUM', 'REVIEW', NOW(), NOW());

-- ============================================
-- ANALYTICS SERVICE DATA
-- ============================================
USE gaadix_analytics_db;

INSERT INTO analytics_events (event_type, user_id, entity_id, entity_type, metadata, created_at) VALUES
('VEHICLE_VIEW', 2, 1, 'VEHICLE', '{"brand":"Maruti Suzuki","model":"Swift VXI"}', NOW()),
('VEHICLE_VIEW', 4, 2, 'VEHICLE', '{"brand":"Hyundai","model":"Creta SX"}', NOW()),
('PAYMENT_COMPLETED', 2, 1, 'PAYMENT', '{"amount":1100000}', NOW()),
('USER_REGISTERED', 2, 2, 'USER', '{"role":"BUYER"}', NOW()),
('VEHICLE_LISTED', 1, 1, 'VEHICLE', '{"brand":"Maruti Suzuki"}', NOW());

-- Verify data insertion
SELECT 'User Service' AS Service, COUNT(*) AS Records FROM gaadix_user_db.users
UNION ALL
SELECT 'Inventory Service', COUNT(*) FROM gaadix_inventory_db.vehicles
UNION ALL
SELECT 'Payment Service', COUNT(*) FROM gaadix_payment_db.payments
UNION ALL
SELECT 'RTO Service', COUNT(*) FROM gaadix_rto_db.rto_verifications
UNION ALL
SELECT 'Notification Service', COUNT(*) FROM gaadix_notification_db.notifications
UNION ALL
SELECT 'Fraud Service', COUNT(*) FROM gaadix_fraud_db.fraud_checks
UNION ALL
SELECT 'Analytics Service', COUNT(*) FROM gaadix_analytics_db.analytics_events;
