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

    @Value("${spring.kafka.order_topic}")
    static final String KAFKA_ORDER_TOPIC = "order_topic";

    @Value("${spring.kafka.consumer.group-id}")
    static final String KAFKA_PROCESSING_GROUP = "processing-group";

    @KafkaListener(topics = KAFKA_ORDER_TOPIC, groupId = KAFKA_PROCESSING_GROUP, containerFactory= "kafkaListenerContainerFactory")

        public void consume(String eventMessage) {
            try{
                log.info("payload - {}", eventMessage);
                OrderEvent event = MAPPER.readValue(eventMessage, OrderEvent.class);
                log.info("Received order event {} {}", event.getStatus(), event.getOrderId());


                if ("orderCreated".equalsIgnoreCase(event.getStatus())) {
                    //OrderEvent orderEvent = MAPPER.readValue(event.getPayload(), OrderEvent.class);
                    notificationService.process(event);
                }}
            catch (Exception e){
                e.printStackTrace();
            }

        }

}