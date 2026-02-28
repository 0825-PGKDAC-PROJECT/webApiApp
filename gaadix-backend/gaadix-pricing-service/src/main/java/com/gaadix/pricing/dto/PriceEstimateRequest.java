package com.gaadix.pricing.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceEstimateRequest {
    
    @NotNull
    private Long carId;
    
    @NotBlank
    private String brand;
    
    @NotBlank
    private String model;
    
    @NotNull
    @Min(1990)
    private Integer year;
    
    @NotNull
    @Min(0)
    private Integer odometer;
    
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal originalPrice;
}
