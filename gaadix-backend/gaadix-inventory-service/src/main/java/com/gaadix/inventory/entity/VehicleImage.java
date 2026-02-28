package com.gaadix.inventory.entity;

import com.gaadix.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vehicle_images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleImage extends BaseEntity {
    
    @Column(nullable = false)
    private Long carId;
    
    @Column(nullable = false, length = 500)
    private String imageUrl;
    
    @Column(nullable = false)
    @Builder.Default
    private Integer displayOrder = 0;
    
    @Builder.Default
    private boolean isPrimary = false;
    
    private String imageType;
}
