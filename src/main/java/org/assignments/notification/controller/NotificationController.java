package org.assignments.notification.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.assignments.notification.dto.DeliveryStatusRequest;
import org.assignments.notification.dto.DeliveryStatusResponse;
import org.assignments.notification.service.DeliveryStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class NotificationController {

    @Autowired
    private DeliveryStatusService deliveryStatusService;


    @PutMapping("/status")
    public ResponseEntity<String> updateStatus(@RequestBody DeliveryStatusRequest request) {

        deliveryStatusService.updateStatus(request);

        return ResponseEntity.ok("Status updated successfully");
    }

    @GetMapping("/status/{orderId}")
    @Operation(summary = "Get notification status order by ID", description = "Fetch notification details using order ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order found"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<List<DeliveryStatusResponse>> getStatusByOrderId(
            @PathVariable Long orderId) {

        long startTime = System.currentTimeMillis();
        try {
            List<DeliveryStatusResponse> deliveryStatusResponse = deliveryStatusService.getByOrderId(orderId);

            if (deliveryStatusResponse == null) {
                log.warn("GET /status/{} - Delivery status not found", orderId);
                return ResponseEntity.notFound().build();
            }

            long timeTaken = System.currentTimeMillis() - startTime;

            log.warn("GET /status/{} - Success | TimeTaken={}ms | Status=FOUND",
                    orderId, timeTaken);

            return ResponseEntity.ok(deliveryStatusResponse);

        } catch (Exception ex) {

            long timeTaken = System.currentTimeMillis() - startTime;

            log.error("GET /status/{} - Failed | TimeTaken={}ms | Error={}",
                    orderId, timeTaken, ex.getMessage(), ex);
            throw ex;
        }
    }
}