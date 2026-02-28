package com.gaadix.rto.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RTOVerificationRequest {
    
    @NotBlank
    @Pattern(regexp = "^[A-Z]{2}[0-9]{1,2}[A-Z]{1,2}[0-9]{4}$", 
             message = "Invalid registration number format")
    private String registrationNumber;
    
    @NotBlank
    private String rtoCode;
    
    private String manufacturer;
    private String model;
    private Integer year;
    private String fuelType;
}
