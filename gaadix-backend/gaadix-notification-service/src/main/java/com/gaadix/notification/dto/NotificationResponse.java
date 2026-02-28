package com.gaadix.notification.dto;

import com.gaadix.notification.entity.Notification.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponse {
    
    private Long id;
    private Long userId;
    private NotificationType type;
    private NotificationChannel channel;
    private String recipient;
    private String subject;
    private String message;
    private NotificationStatus status;
    private LocalDateTime sentAt;
    private String errorMessage;
    private Integer retryCount;
    private LocalDateTime createdAt;
}
