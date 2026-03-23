package org.assignments.notification.repository;

import org.assignments.notification.entity.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeliveryStatusRepository extends JpaRepository<DeliveryStatus, Long> {
    @Query("SELECT ds FROM DeliveryStatus ds JOIN ds.notification n WHERE n.orderId = :orderId")
    List<DeliveryStatus> findByOrderId(@Param("orderId") Long orderId);
}
