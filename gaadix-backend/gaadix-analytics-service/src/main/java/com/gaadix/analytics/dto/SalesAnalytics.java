package com.gaadix.analytics.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesAnalytics {
    
    private Map<String, Long> monthlySales;
    private BigDecimal averagePrice;
    private Long totalSales;
    private Double growthRate;
}
