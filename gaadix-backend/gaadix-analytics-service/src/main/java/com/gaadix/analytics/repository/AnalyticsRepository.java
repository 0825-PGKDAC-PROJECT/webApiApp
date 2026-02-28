package com.gaadix.analytics.repository;

import com.gaadix.analytics.entity.AnalyticsEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalyticsRepository extends JpaRepository<AnalyticsEvent, Long> {
    
    List<AnalyticsEvent> findByEventType(String eventType);
    
    List<AnalyticsEvent> findByUserId(Long userId);
    
    long countByEventType(String eventType);
}
