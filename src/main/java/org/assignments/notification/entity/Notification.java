package org.assignments.notification.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.assignments.notification.enums.DeliveryStatusType;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "notification_type")
    private String notificationType;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String message;

    private String channel;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // 🔗 One Notification → Many DeliveryStatus
    @JsonManagedReference
    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DeliveryStatus> deliveryStatuses;
}