package com.gaadix.fraud.dto;

import com.gaadix.fraud.entity.FraudCheck.RiskLevel;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FraudCheckResponse {
    
    private Long id;
    private Long userId;
    private Long transactionId;
    private Long carId;
    private BigDecimal amount;
    private Integer riskScore;
    private RiskLevel riskLevel;
    private Boolean isFraudulent;
    private List<String> riskFactors;
    private String recommendation;
}
