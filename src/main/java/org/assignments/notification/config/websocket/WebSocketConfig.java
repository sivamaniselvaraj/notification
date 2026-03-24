package org.assignments.notification.config.websocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${websocket.broker}")
    private String WEBSOCKET_SIMPLE_BROKER;

    @Value("${websocket.application_destination_prefixes}")
    private String WEBSOCKET_APPLICATION_DESTINATION_PREFIXES;

    @Value("${websocket.end_point}")
    private String WEBSOCKET_END_POINT;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(WEBSOCKET_SIMPLE_BROKER);
        registry.setApplicationDestinationPrefixes(WEBSOCKET_APPLICATION_DESTINATION_PREFIXES);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(WEBSOCKET_END_POINT)
                .setAllowedOrigins("*","http://localhost:4200")
                .withSockJS();
    }
}
