package org.assignments.notification.service;

import org.assignments.notification.dto.DeliveryStatusRequest;
import org.assignments.notification.dto.DeliveryStatusResponse;
import org.assignments.notification.entity.DeliveryStatus;
import org.assignments.notification.repository.DeliveryStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeliveryStatusService {

    @Autowired
    private DeliveryStatusRepository deliveryStatusRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void updateStatus(DeliveryStatusRequest request) {

        DeliveryStatus status = deliveryStatusRepository.findById(request.getDeliveryId())
                .orElseThrow(() -> new RuntimeException("Delivery not found"));

        status.setDeliveryStatus(request.getStatus());
        status.setDeliveredAt(LocalDateTime.now());

        if ("FAILED".equalsIgnoreCase(request.getStatus())) {
            status.setRetryCount(status.getRetryCount() + 1);
            status.setErrorMessage(request.getErrorMessage());
        }

        deliveryStatusRepository.save(status);

        // Push update to UI
        messagingTemplate.convertAndSend(
                "/topic/delivery-status",
                status
        );
    }

    public List<DeliveryStatusResponse> getByOrderId(Long orderId) {

        List<DeliveryStatus> statuses = deliveryStatusRepository.findByOrderId(orderId);

        if (statuses.isEmpty()) {
            throw new RuntimeException("No delivery status found for orderId: " + orderId);
        }

        return statuses.stream()
                .map(ds -> new DeliveryStatusResponse(
                        ds.getDeliveryId(),
                        ds.getNotification().getNotificationId(),
                        ds.getDeliveryStatus(),
                        ds.getDeliveredAt()
                ))
                .toList();
    }
}
