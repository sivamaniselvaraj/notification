package org.assignments.notification.consumer;

import lombok.extern.slf4j.Slf4j;
import org.assignments.notification.dto.OrderEvent;
import org.assignments.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@Slf4j
public class OrderEventConsumer {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private NotificationService notificationService;

    @Value("${spring.kafka.notification_topic}")
    static final String KAFKA_NOTIFICATION_TOPIC = "notification_topic";

    @Value("${spring.kafka.consumer.group-id}")
    static final String KAFKA_NOTIFICATION_GROUP = "notification-group";

    @KafkaListener(topics = KAFKA_NOTIFICATION_TOPIC, groupId = KAFKA_NOTIFICATION_GROUP, containerFactory= "kafkaListenerContainerFactory")

        public void consume(String eventMessage) {
            try{
                log.info("payload - {}", eventMessage);
                OrderEvent event = MAPPER.readValue(eventMessage, OrderEvent.class);
                log.info("Received order event {} {}", event.getStatus(), event.getOrderId());

//
//                if ("orderCreated".equalsIgnoreCase(event.getStatus())) {
//                    //OrderEvent orderEvent = MAPPER.readValue(event.getPayload(), OrderEvent.class);
//                    notificationService.process(event);
//                }
                notificationService.process(event);
            }
            catch (Exception e){
                log.info(e.getMessage());
            }

        }

}