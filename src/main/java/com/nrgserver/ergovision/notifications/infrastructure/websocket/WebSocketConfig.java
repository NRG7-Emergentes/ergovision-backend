package com.nrgserver.ergovision.notifications.infrastructure.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-notifications") // endpoint base
                .setAllowedOriginPatterns("*")
                .withSockJS(); // soporte para clientes que no usan WebSocket nativo
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app"); // prefijo para mensajes entrantes
        registry.enableSimpleBroker("/topic", "/queue", "/user");
        registry.setUserDestinationPrefix("/user"); // para mensajes privados
    }
}
