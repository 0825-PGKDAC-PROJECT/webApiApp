package com.gaadix.common.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "price_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceHistory extends BaseEntity {
    
    private Long carId;
    private BigDecimal oldPrice;
    private BigDecimal newPrice;
    private String reason;
    private String changedBy;
}
