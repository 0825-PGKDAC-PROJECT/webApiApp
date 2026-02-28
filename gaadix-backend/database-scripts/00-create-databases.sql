-- ============================================
-- GaadiX Database Initialization Script
-- Creates all 10 microservice databases
-- ============================================

-- Drop existing databases (optional - uncomment if needed)
-- DROP DATABASE IF EXISTS gaadix_user_db;
-- DROP DATABASE IF EXISTS gaadix_inventory_db;
-- DROP DATABASE IF EXISTS gaadix_search_db;
-- DROP DATABASE IF EXISTS gaadix_payment_db;
-- DROP DATABASE IF EXISTS gaadix_rto_db;
-- DROP DATABASE IF EXISTS gaadix_pricing_db;
-- DROP DATABASE IF EXISTS gaadix_notification_db;
-- DROP DATABASE IF EXISTS gaadix_fraud_db;
-- DROP DATABASE IF EXISTS gaadix_analytics_db;

-- Create databases
CREATE DATABASE IF NOT EXISTS gaadix_user_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS gaadix_inventory_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS gaadix_search_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS gaadix_payment_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS gaadix_rto_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS gaadix_pricing_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS gaadix_notification_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS gaadix_fraud_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS gaadix_analytics_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Verify databases created
SHOW DATABASES LIKE 'gaadix_%';

-- Grant privileges (adjust username as needed)
GRANT ALL PRIVILEGES ON gaadix_user_db.* TO 'KD2-GANESH-92393'@'localhost';
GRANT ALL PRIVILEGES ON gaadix_inventory_db.* TO 'KD2-GANESH-92393'@'localhost';
GRANT ALL PRIVILEGES ON gaadix_search_db.* TO 'KD2-GANESH-92393'@'localhost';
GRANT ALL PRIVILEGES ON gaadix_payment_db.* TO 'KD2-GANESH-92393'@'localhost';
GRANT ALL PRIVILEGES ON gaadix_rto_db.* TO 'KD2-GANESH-92393'@'localhost';
GRANT ALL PRIVILEGES ON gaadix_pricing_db.* TO 'KD2-GANESH-92393'@'localhost';
GRANT ALL PRIVILEGES ON gaadix_notification_db.* TO 'KD2-GANESH-92393'@'localhost';
GRANT ALL PRIVILEGES ON gaadix_fraud_db.* TO 'KD2-GANESH-92393'@'localhost';
GRANT ALL PRIVILEGES ON gaadix_analytics_db.* TO 'KD2-GANESH-92393'@'localhost';
FLUSH PRIVILEGES;
