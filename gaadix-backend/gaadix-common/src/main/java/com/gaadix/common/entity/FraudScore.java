package com.gaadix.common.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "fraud_scores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FraudScore extends BaseEntity {
    
    private Long userId;
    private Long carId;
    
    private Double riskScore;
    private String riskLevel;
    
    @Column(length = 1000)
    private String factors;
    
    @Builder.Default
    private boolean isReviewed = false;
    
    private String reviewedBy;
    private String reviewNotes;
}
