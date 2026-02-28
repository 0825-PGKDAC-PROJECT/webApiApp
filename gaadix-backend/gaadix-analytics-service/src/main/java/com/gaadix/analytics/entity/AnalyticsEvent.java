package com.gaadix.analytics.entity;

import com.gaadix.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "analytics_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalyticsEvent extends BaseEntity {
    
    @Column(nullable = false)
    private String eventType;
    
    @Column(length = 2000)
    private String metadata;
    
    private Long userId;
    private Long entityId;
    private String entityType;
}
