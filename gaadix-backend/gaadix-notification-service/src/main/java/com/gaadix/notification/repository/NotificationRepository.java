package com.gaadix.notification.repository;

import com.gaadix.notification.entity.Notification;
import com.gaadix.notification.entity.Notification.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);
    
    List<Notification> findByStatus(NotificationStatus status);
    
    List<Notification> findByChannelAndStatus(NotificationChannel channel, NotificationStatus status);
    
    List<Notification> findByTypeAndUserId(NotificationType type, Long userId);
}
