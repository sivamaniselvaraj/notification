package org.assignments.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEvent {
    private String eventType;
    private Long orderId;
    private String correlationId;
    private String status;
    private String idempotencyKey;
}