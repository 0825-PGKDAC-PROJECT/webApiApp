package com.gaadix.notification.dto;

import com.gaadix.notification.entity.Notification.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequest {
    
    @NotNull
    private Long userId;
    
    @NotNull
    private NotificationType type;
    
    @NotNull
    private NotificationChannel channel;
    
    @NotBlank
    private String recipient;
    
    @NotBlank
    private String subject;
    
    @NotBlank
    private String message;
}
