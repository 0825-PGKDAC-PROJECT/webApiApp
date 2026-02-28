package com.gaadix.fraud.entity;

import com.gaadix.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "fraud_checks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FraudCheck extends BaseEntity {
    
    @Column(nullable = false)
    private Long userId;
    
    @Column(nullable = false)
    private Long transactionId;
    
    @Column(nullable = false)
    private Long carId;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;
    
    @Column(nullable = false)
    private Integer riskScore;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RiskLevel riskLevel;
    
    @Column(nullable = false)
    private Boolean isFraudulent;
    
    @Column(length = 1000)
    private String riskFactors;
    
    @Column(length = 500)
    private String recommendation;
    
    private String ipAddress;
    private String deviceId;
    private String location;
    
    public enum RiskLevel {
        LOW, MEDIUM, HIGH, CRITICAL
    }
}
