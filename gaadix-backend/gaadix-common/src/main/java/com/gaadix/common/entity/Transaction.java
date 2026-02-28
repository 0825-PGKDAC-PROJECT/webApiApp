package com.gaadix.common.entity;

import com.gaadix.common.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction extends BaseEntity {
    
    @Column(unique = true)
    private String transactionId;
    
    private Long buyerId;
    private Long sellerId;
    private Long carId;
    
    private BigDecimal amount;
    private BigDecimal platformFee;
    private BigDecimal totalAmount;
    
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TransactionStatus status = TransactionStatus.INITIATED;
    
    private String paymentMethod;
    private String paymentGatewayRef;
    
    @Column(length = 500)
    private String remarks;
}
