package com.gaadix.notification.service;

import com.gaadix.notification.dto.NotificationRequest;
import com.gaadix.notification.dto.NotificationResponse;
import com.gaadix.notification.entity.Notification;
import com.gaadix.notification.entity.Notification.*;
import com.gaadix.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    
    private final NotificationRepository repository;
    
    @Transactional
    public NotificationResponse sendNotification(NotificationRequest request) {
        Notification notification = Notification.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .channel(request.getChannel())
                .recipient(request.getRecipient())
                .subject(request.getSubject())
                .message(request.getMessage())
                .status(NotificationStatus.PENDING)
                .build();
        
        Notification saved = repository.save(notification);
        
        boolean sent = sendViaChannel(saved);
        
        if (sent) {
            saved.setStatus(NotificationStatus.SENT);
            saved.setSentAt(LocalDateTime.now());
        } else {
            saved.setStatus(NotificationStatus.FAILED);
            saved.setErrorMessage("Mock send failed");
        }
        
        Notification updated = repository.save(saved);
        return mapToResponse(updated);
    }
    
    @Transactional(readOnly = true)
    public List<NotificationResponse> getUserNotifications(Long userId) {
        return repository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public NotificationResponse getNotification(Long id) {
        Notification notification = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        return mapToResponse(notification);
    }
    
    private boolean sendViaChannel(Notification notification) {
        log.info("Sending {} notification to {} via {}", 
                notification.getType(), notification.getRecipient(), notification.getChannel());
        
        return switch (notification.getChannel()) {
            case EMAIL -> sendEmail(notification);
            case SMS -> sendSMS(notification);
            case PUSH -> sendPush(notification);
        };
    }
    
    private boolean sendEmail(Notification notification) {
        log.info("Mock Email sent to: {}, Subject: {}", 
                notification.getRecipient(), notification.getSubject());
        return true;
    }
    
    private boolean sendSMS(Notification notification) {
        log.info("Mock SMS sent to: {}, Message: {}", 
                notification.getRecipient(), notification.getMessage());
        return true;
    }
    
    private boolean sendPush(Notification notification) {
        log.info("Mock Push notification sent to user: {}", notification.getUserId());
        return true;
    }
    
    private NotificationResponse mapToResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .userId(notification.getUserId())
                .type(notification.getType())
                .channel(notification.getChannel())
                .recipient(notification.getRecipient())
                .subject(notification.getSubject())
                .message(notification.getMessage())
                .status(notification.getStatus())
                .sentAt(notification.getSentAt())
                .errorMessage(notification.getErrorMessage())
                .retryCount(notification.getRetryCount())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}
