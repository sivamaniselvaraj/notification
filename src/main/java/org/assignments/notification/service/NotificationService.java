package org.assignments.notification.service;

import lombok.extern.slf4j.Slf4j;
import org.assignments.notification.dto.OrderEvent;
import org.assignments.notification.entity.Notification;
import org.assignments.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        notification.setMessage("Order Created");
        notification.setCreatedAt(LocalDateTime.now());

        notificationrepository.save(notification);

        // 🔥 Push to UI
        messagingTemplate.convertAndSend(
                "/topic/notifications",
                notification
        );

        log.info("Notification pushed to UI for order {}", event.getOrderId());
    }
}
