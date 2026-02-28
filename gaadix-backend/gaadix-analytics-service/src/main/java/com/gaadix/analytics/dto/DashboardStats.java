package com.gaadix.analytics.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardStats {
    
    private Long totalUsers;
    private Long totalVehicles;
    private Long totalTransactions;
    private BigDecimal totalRevenue;
    private Long activeListings;
    private Long pendingVerifications;
}
