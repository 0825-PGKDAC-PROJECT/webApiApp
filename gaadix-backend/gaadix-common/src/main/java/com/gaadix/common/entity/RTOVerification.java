package com.gaadix.common.entity;

import com.gaadix.common.enums.FuelType;
import com.gaadix.common.enums.VerificationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "rto_verifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RTOVerification extends BaseEntity {
    
    @Column(unique = true)
    private String registrationNumber;
    
    private String rtoCode;
    private String ownerName;
    private LocalDate registrationDate;
    private LocalDate manufacturingDate;
    private String chassisNumber;
    private String engineNumber;
    private String vehicleClass;
    private String vehicleCategory;
    
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;
    
    private String emissionNorm;
    private String insuranceCompany;
    private String insurancePolicyNumber;
    private LocalDate insuranceValidUpto;
    private LocalDate fitnessValidUpto;
    private LocalDate pucValidUpto;
    
    private BigDecimal roadTaxPaid;
    private LocalDate roadTaxValidUpto;
    
    @Builder.Default
    private boolean nocIssued = false;
    
    private String nocIssuedBy;
    private LocalDate nocDate;
    
    @Builder.Default
    private boolean isFinanced = false;
    
    private String financierName;
    
    @Builder.Default
    private boolean hypothecationRemoved = false;
    
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private VerificationStatus verificationStatus = VerificationStatus.PENDING;
    
    private LocalDateTime verifiedAt;
    private String verifiedBy;
    
    @Builder.Default
    private boolean hasOutstandingChallan = false;
    
    @Builder.Default
    private int challanCount = 0;
    
    private BigDecimal totalChallanAmount;
    
    @Builder.Default
    private boolean isBlacklisted = false;
    
    private String blacklistReason;
    
    @Builder.Default
    private String mockDataSource = "SIMULATED_VAHAN";
    
    @Builder.Default
    private boolean isRealData = false;
}
