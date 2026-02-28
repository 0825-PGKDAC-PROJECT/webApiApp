package com.gaadix.payment.dto;

import com.gaadix.payment.entity.Payment.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {
    
    private Long id;
    private Long transactionId;
    private Long carId;
    private Long buyerId;
    private Long sellerId;
    private BigDecimal amount;
    private PaymentStatus status;
    private PaymentMethod method;
    private String gatewayTransactionId;
    private LocalDateTime paidAt;
    private LocalDateTime refundedAt;
    private String remarks;
    private LocalDateTime createdAt;
}
