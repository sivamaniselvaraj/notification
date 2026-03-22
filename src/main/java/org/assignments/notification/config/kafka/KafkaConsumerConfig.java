package org.assignments.notification.config.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.assignments.notification.dto.OrderEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String KAFKA_BOOTSTRAP_SERVER;

    @Value("${spring.kafka.consumer.group-id}")
    private String KAFKA_PROCESSING_CONSUMER_GROUP;

    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String KAFKA_GROUP_AUTO_OFFSET_RESET;

    @Value("${spring.kafka.consumer.retry_backoff_ms}")
    private String KAFKA_RETRY_BACKOFF_MILLI_SEC;

    /*
     Consumer Configuration
     */
    @Bean
    public ConsumerFactory<String, OrderEvent> consumerFactory() {

        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BOOTSTRAP_SERVER);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, KAFKA_PROCESSING_CONSUMER_GROUP);

//        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JacksonJsonDeserializer.class);

        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, KAFKA_GROUP_AUTO_OFFSET_RESET);
        config.put(ConsumerConfig.RETRY_BACKOFF_MS_CONFIG, KAFKA_RETRY_BACKOFF_MILLI_SEC);

        JacksonJsonDeserializer<OrderEvent> payloadJsonDeserializer = new JacksonJsonDeserializer<>();
        payloadJsonDeserializer.addTrustedPackages("org.assignments.notification.dto");
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), payloadJsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderEvent>
    kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, OrderEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());

        return factory;
    }

}
