package com.example.Restaurant_Ordering_System.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");  // clients subscribe here
        registry.setApplicationDestinationPrefixes("/app"); // client send here (if needed)
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")   // frontend connects to ws://host/ws
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}
