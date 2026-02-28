package com.gaadix.fraud.service;

import com.gaadix.fraud.dto.FraudCheckRequest;
import com.gaadix.fraud.dto.FraudCheckResponse;
import com.gaadix.fraud.entity.FraudCheck;
import com.gaadix.fraud.entity.FraudCheck.RiskLevel;
import com.gaadix.fraud.repository.FraudCheckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FraudService {
    
    private final FraudCheckRepository repository;
    private static final int RISK_THRESHOLD = 70;
    
    @Transactional
    public FraudCheckResponse checkFraud(FraudCheckRequest request) {
        List<String> riskFactors = new ArrayList<>();
        int riskScore = 0;
        
        // Check 1: High transaction amount
        if (request.getAmount().compareTo(new BigDecimal("1000000")) > 0) {
            riskScore += 30;
            riskFactors.add("High transaction amount (>10L)");
        }
        
        // Check 2: Multiple transactions from same user
        long userTransactionCount = repository.countByUserId(request.getUserId());
        if (userTransactionCount > 5) {
            riskScore += 20;
            riskFactors.add("Multiple transactions from same user");
        }
        
        // Check 3: Suspicious IP or location
        if (request.getIpAddress() != null && request.getIpAddress().startsWith("192.168")) {
            riskScore += 10;
            riskFactors.add("Suspicious IP address");
        }
        
        // Check 4: New user with high value transaction
        if (userTransactionCount == 0 && request.getAmount().compareTo(new BigDecimal("500000")) > 0) {
            riskScore += 25;
            riskFactors.add("New user with high value transaction");
        }
        
        // Check 5: Rapid transactions
        long recentTransactions = repository.countRecentTransactionsByUser(request.getUserId());
        if (recentTransactions > 3) {
            riskScore += 15;
            riskFactors.add("Rapid successive transactions");
        }
        
        RiskLevel riskLevel = determineRiskLevel(riskScore);
        boolean isFraudulent = riskScore >= RISK_THRESHOLD;
        String recommendation = generateRecommendation(riskScore, riskLevel);
        
        FraudCheck fraudCheck = FraudCheck.builder()
                .userId(request.getUserId())
                .transactionId(request.getTransactionId())
                .carId(request.getCarId())
                .amount(request.getAmount())
                .riskScore(riskScore)
                .riskLevel(riskLevel)
                .isFraudulent(isFraudulent)
                .riskFactors(String.join(", ", riskFactors))
                .recommendation(recommendation)
                .ipAddress(request.getIpAddress())
                .deviceId(request.getDeviceId())
                .location(request.getLocation())
                .build();
        
        FraudCheck saved = repository.save(fraudCheck);
        return mapToResponse(saved, riskFactors);
    }
    
    @Transactional(readOnly = true)
    public FraudCheckResponse getFraudCheck(Long transactionId) {
        FraudCheck fraudCheck = repository.findByTransactionId(transactionId)
                .orElseThrow(() -> new RuntimeException("Fraud check not found"));
        List<String> factors = List.of(fraudCheck.getRiskFactors().split(", "));
        return mapToResponse(fraudCheck, factors);
    }
    
    private RiskLevel determineRiskLevel(int score) {
        if (score >= 80) return RiskLevel.CRITICAL;
        if (score >= 60) return RiskLevel.HIGH;
        if (score >= 30) return RiskLevel.MEDIUM;
        return RiskLevel.LOW;
    }
    
    private String generateRecommendation(int score, RiskLevel level) {
        return switch (level) {
            case CRITICAL -> "Block transaction immediately. Manual review required.";
            case HIGH -> "Hold transaction for verification. Contact user.";
            case MEDIUM -> "Proceed with additional verification steps.";
            case LOW -> "Transaction approved. Low risk detected.";
        };
    }
    
    private FraudCheckResponse mapToResponse(FraudCheck check, List<String> factors) {
        return FraudCheckResponse.builder()
                .id(check.getId())
                .userId(check.getUserId())
                .transactionId(check.getTransactionId())
                .carId(check.getCarId())
                .amount(check.getAmount())
                .riskScore(check.getRiskScore())
                .riskLevel(check.getRiskLevel())
                .isFraudulent(check.getIsFraudulent())
                .riskFactors(factors)
                .recommendation(check.getRecommendation())
                .build();
    }
}
