package com.gaadix.notification.controller;

import com.gaadix.common.dto.ApiResponse;
import com.gaadix.notification.dto.NotificationRequest;
import com.gaadix.notification.dto.NotificationResponse;
import com.gaadix.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {
    
    private final NotificationService notificationService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<NotificationResponse>> sendNotification(
            @Valid @RequestBody NotificationRequest request) {
        NotificationResponse response = notificationService.sendNotification(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Notification sent successfully"));
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<NotificationResponse>>> getUserNotifications(
            @PathVariable Long userId) {
        List<NotificationResponse> response = notificationService.getUserNotifications(userId);
        return ResponseEntity.ok(ApiResponse.success(response, "Notifications retrieved successfully"));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NotificationResponse>> getNotification(
            @PathVariable Long id) {
        NotificationResponse response = notificationService.getNotification(id);
        return ResponseEntity.ok(ApiResponse.success(response, "Notification retrieved successfully"));
    }
}
