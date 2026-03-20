package org.assignments.notification.consumer;

import org.assignments.notification.dto.OrderEvent;
import org.assignments.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventConsumer {

    @Autowired
    private NotificationService notificationService;

    @Value("${spring.kafka.order_topic}")
    static final String KAFKA_ORDER_TOPIC = "order_topic";

    @Value("${spring.kafka.consumer.group-id}")
    static final String KAFKA_PROCESSING_GROUP = "processing-group";

    @KafkaListener(topics = KAFKA_ORDER_TOPIC, groupId = KAFKA_PROCESSING_GROUP)
    public void consume(OrderEvent event) {
        notificationService.process(event);
    }
}