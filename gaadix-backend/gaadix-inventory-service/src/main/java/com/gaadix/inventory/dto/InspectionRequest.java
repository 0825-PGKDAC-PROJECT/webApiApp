package com.gaadix.inventory.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InspectionRequest {
    
    @NotNull
    private Long carId;
    
    @NotNull
    private LocalDate inspectionDate;
    
    private String inspectorName;
    
    @NotNull
    @Min(0)
    @Max(100)
    private Integer overallScore;
    
    @Min(0)
    @Max(100)
    private Integer engineScore;
    
    @Min(0)
    @Max(100)
    private Integer transmissionScore;
    
    @Min(0)
    @Max(100)
    private Integer suspensionScore;
    
    @Min(0)
    @Max(100)
    private Integer brakesScore;
    
    @Min(0)
    @Max(100)
    private Integer electricalScore;
    
    @Min(0)
    @Max(100)
    private Integer bodyScore;
    
    @Min(0)
    @Max(100)
    private Integer interiorScore;
    
    private String notes;
    private String recommendations;
}
