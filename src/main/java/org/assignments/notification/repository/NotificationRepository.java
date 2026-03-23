package org.assignments.notification.repository;

import org.assignments.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
