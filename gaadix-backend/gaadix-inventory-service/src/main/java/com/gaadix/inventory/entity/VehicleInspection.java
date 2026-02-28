package com.gaadix.inventory.entity;

import com.gaadix.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "vehicle_inspections")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleInspection extends BaseEntity {
    
    @Column(nullable = false)
    private Long carId;
    
    @Column(nullable = false)
    private LocalDate inspectionDate;
    
    private String inspectorName;
    
    @Column(nullable = false)
    private Integer overallScore;
    
    private Integer engineScore;
    private Integer transmissionScore;
    private Integer suspensionScore;
    private Integer brakesScore;
    private Integer electricalScore;
    private Integer bodyScore;
    private Integer interiorScore;
    
    @Column(length = 2000)
    private String notes;
    
    @Column(length = 1000)
    private String recommendations;
    
    @Builder.Default
    private boolean isVerified = false;
}
