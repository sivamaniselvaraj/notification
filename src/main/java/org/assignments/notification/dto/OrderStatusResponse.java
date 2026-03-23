package org.assignments.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderStatusResponse {

    private Long orderId;
    private String overallStatus;
    private List<DeliveryStatusResponse> details;

}