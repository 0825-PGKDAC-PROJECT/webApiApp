package com.gaadix.payment.entity;

import com.gaadix.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment extends BaseEntity {
    
    @Column(nullable = false)
    private Long transactionId;
    
    @Column(nullable = false)
    private Long carId;
    
    @Column(nullable = false)
    private Long buyerId;
    
    @Column(nullable = false)
    private Long sellerId;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private PaymentStatus status = PaymentStatus.PENDING;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod method;
    
    private String gatewayTransactionId;
    private String gatewayResponse;
    
    private LocalDateTime paidAt;
    private LocalDateTime refundedAt;
    
    @Column(length = 500)
    private String remarks;
    
    public enum PaymentStatus {
        PENDING, PROCESSING, COMPLETED, FAILED, REFUNDED, CANCELLED
    }
    
    public enum PaymentMethod {
        CREDIT_CARD, DEBIT_CARD, UPI, NET_BANKING, WALLET, EMI
    }
}
