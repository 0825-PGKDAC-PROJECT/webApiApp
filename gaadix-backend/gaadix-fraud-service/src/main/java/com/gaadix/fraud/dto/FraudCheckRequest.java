package com.gaadix.fraud.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FraudCheckRequest {
    
    @NotNull
    private Long userId;
    
    @NotNull
    private Long transactionId;
    
    @NotNull
    private Long carId;
    
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;
    
    private String ipAddress;
    private String deviceId;
    private String location;
}
