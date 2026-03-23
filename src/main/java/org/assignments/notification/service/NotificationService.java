package org.assignments.notification.service;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.extern.slf4j.Slf4j;
import org.assignments.notification.dto.DeliveryStatusResponse;
import org.assignments.notification.dto.OrderEvent;
import org.assignments.notification.entity.DeliveryStatus;
import org.assignments.notification.entity.Notification;
import org.assignments.notification.enums.Channels;
import org.assignments.notification.enums.DeliveryStatusType;
import org.assignments.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class NotificationService {
    @Autowired
    private NotificationRepository notificationrepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void process(OrderEvent event) {
        Notification notification = new Notification();
        notification.setNotificationId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
        notification.setUserId(event.getOrderId());
        notification.setOrderId(event.getOrderId());
        notification.setChannel(Channels.WEBSOCKET.name());
        notification.setMessage(event.getStatus());
        notification.setCreatedAt(LocalDateTime.now());

        DeliveryStatus status = DeliveryStatus.builder()
                .deliveryId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE)
                .deliveryStatus(DeliveryStatusType.PENDING.toString())
                .notification(notification)
                .build();
        notification.setDeliveryStatuses(List.of(status));

        log.info("inside notification processing  {}, notification details {}", event.getCorrelationId(), notification);
        notificationrepository.save(notification);

        // 🔥 Push to UI
        messagingTemplate.convertAndSend(
                "/topic/notifications",
                notification
        );

        log.info("Notification pushed to UI for order {}", event.getOrderId());
    }
}
