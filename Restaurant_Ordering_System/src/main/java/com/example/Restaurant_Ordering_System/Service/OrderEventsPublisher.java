package com.example.Restaurant_Ordering_System.Service;

import com.example.Restaurant_Ordering_System.DTO.OrderDtos.OrderResponse;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventsPublisher {
    private final SimpMessagingTemplate template;

    public OrderEventsPublisher(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void publishOrderUpdate(Long orderId, OrderResponse payload) {
        // Frontend subscribes to /topic/orders/{orderId}
        template.convertAndSend("/topic/orders/" + orderId, payload);
    }
}
