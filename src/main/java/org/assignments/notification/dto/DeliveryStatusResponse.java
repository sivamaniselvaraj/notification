package org.assignments.notification.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryStatusResponse {

    private Long deliveryId;
    private Long notificationId;
    private String status;
    private LocalDateTime deliveredAt;

}
