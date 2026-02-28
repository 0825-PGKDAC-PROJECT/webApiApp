package com.gaadix.notification.service;

import com.gaadix.notification.dto.NotificationRequest;
import com.gaadix.notification.dto.NotificationResponse;
import com.gaadix.notification.entity.Notification;
import com.gaadix.notification.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository repository;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    void testSendNotification_Email() {
        NotificationRequest request = new NotificationRequest();
        request.setUserId(1L);
        request.setType(Notification.NotificationType.REGISTRATION);
        request.setChannel(Notification.NotificationChannel.EMAIL);
        request.setRecipient("test@example.com");
        request.setSubject("Welcome");
        request.setMessage("Welcome to GaadiX");

        Notification notification = Notification.builder()
                .userId(1L)
                .type(Notification.NotificationType.REGISTRATION)
                .channel(Notification.NotificationChannel.EMAIL)
                .recipient("test@example.com")
                .status(Notification.NotificationStatus.SENT)
                .build();
        notification.setId(1L);
        when(repository.save(any())).thenReturn(notification);

        NotificationResponse response = notificationService.sendNotification(request);
        assertThat(response).isNotNull();
    }

    @Test
    void testSendNotification_SMS() {
        NotificationRequest request = new NotificationRequest();
        request.setUserId(1L);
        request.setType(Notification.NotificationType.PAYMENT);
        request.setChannel(Notification.NotificationChannel.SMS);
        request.setRecipient("+919876543210");
        request.setSubject("Payment");
        request.setMessage("Payment successful");

        Notification notification = Notification.builder()
                .userId(1L)
                .type(Notification.NotificationType.PAYMENT)
                .channel(Notification.NotificationChannel.SMS)
                .recipient("+919876543210")
                .status(Notification.NotificationStatus.SENT)
                .build();
        notification.setId(1L);
        when(repository.save(any())).thenReturn(notification);

        NotificationResponse response = notificationService.sendNotification(request);
        assertThat(response).isNotNull();
    }
}
