package com.gaadix.notification.entity;

import com.gaadix.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification extends BaseEntity {
    
    @Column(nullable = false)
    private Long userId;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType type;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationChannel channel;
    
    @Column(nullable = false)
    private String recipient;
    
    @Column(nullable = false)
    private String subject;
    
    @Column(nullable = false, length = 2000)
    private String message;
    
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private NotificationStatus status = NotificationStatus.PENDING;
    
    private LocalDateTime sentAt;
    private String errorMessage;
    
    @Builder.Default
    private Integer retryCount = 0;
    
    public enum NotificationType {
        REGISTRATION, LOGIN, PAYMENT, BOOKING, VERIFICATION, ALERT, PROMOTIONAL
    }
    
    public enum NotificationChannel {
        EMAIL, SMS, PUSH
    }
    
    public enum NotificationStatus {
        PENDING, SENT, FAILED, RETRY
    }
}
