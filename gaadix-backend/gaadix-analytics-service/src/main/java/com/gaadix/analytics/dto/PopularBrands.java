package com.gaadix.analytics.dto;

import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PopularBrands {
    
    private Map<String, Long> brandCounts;
    private String topBrand;
    private Long totalBrands;
}
