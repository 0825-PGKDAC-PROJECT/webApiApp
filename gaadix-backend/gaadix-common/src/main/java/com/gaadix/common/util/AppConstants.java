package com.gaadix.common.util;

public class AppConstants {
    
    // Pagination
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int MAX_PAGE_SIZE = 100;
    public static final String DEFAULT_SORT_BY = "createdAt";
    public static final String DEFAULT_SORT_DIR = "desc";
    
    // JWT
    public static final long JWT_EXPIRATION_MS = 86400000; // 24 hours
    public static final long REFRESH_TOKEN_EXPIRATION_MS = 604800000; // 7 days
    
    // File Upload
    public static final long MAX_FILE_SIZE = 5242880; // 5MB
    public static final String[] ALLOWED_IMAGE_TYPES = {"image/jpeg", "image/png", "image/jpg"};
    
    // RTO
    public static final int RTO_VERIFICATION_TIMEOUT_SECONDS = 5;
    public static final String MOCK_RTO_SOURCE = "SIMULATED_VAHAN";
    
    // Pricing
    public static final double PLATFORM_FEE_PERCENTAGE = 2.5;
    public static final double MIN_PRICE_THRESHOLD = 50000.0;
    public static final double MAX_PRICE_THRESHOLD = 50000000.0;
    
    // Fraud Detection
    public static final double FRAUD_SCORE_CRITICAL = 70.0;
    public static final double FRAUD_SCORE_HIGH = 50.0;
    public static final double FRAUD_SCORE_MEDIUM = 30.0;
    
    private AppConstants() {}
}
