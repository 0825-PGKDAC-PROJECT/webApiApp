package com.gaadix.payment.dto;

import com.gaadix.payment.entity.Payment.PaymentMethod;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {
    
    @NotNull
    private Long transactionId;
    
    @NotNull
    private Long carId;
    
    @NotNull
    private Long buyerId;
    
    @NotNull
    private Long sellerId;
    
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;
    
    @NotNull
    private PaymentMethod method;
    
    private String remarks;
}
