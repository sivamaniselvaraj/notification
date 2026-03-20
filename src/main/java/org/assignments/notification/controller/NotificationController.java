package org.assignments.notification.controller;

import org.assignments.notification.dto.DeliveryStatusRequest;
import org.assignments.notification.dto.DeliveryStatusResponse;
import org.assignments.notification.service.DeliveryStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private DeliveryStatusService deliveryStatusService;


    @PutMapping("/status")
    public ResponseEntity<String> updateStatus(@RequestBody DeliveryStatusRequest request) {

        deliveryStatusService.updateStatus(request);

        return ResponseEntity.ok("Status updated successfully");
    }

    @GetMapping("/status/{orderId}")
    public ResponseEntity<List<DeliveryStatusResponse>> getStatusByOrderId(
            @PathVariable Long orderId) {

        List<DeliveryStatusResponse> response = deliveryStatusService.getByOrderId(orderId);

        return ResponseEntity.ok(response);
    }
}