package com.nrgserver.ergovision.notifications.infrastructure.websocket;

import com.nrgserver.ergovision.notifications.application.websocket.NotificationWebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WebSocketNotificationController {

    private final NotificationWebSocketService notificationWebSocketService;

    // Mensaje público (por ejemplo: broadcast)
    @MessageMapping("/application")
    @SendTo("/topic/all")
    public String broadcastMessage(String message, SimpMessageHeaderAccessor headerAccessor) {
        return notificationWebSocketService.processIncomingMessage(message);
    }

    // Mensaje privado
    @MessageMapping("/private")
    public void sendPrivate(String message, SimpMessageHeaderAccessor headerAccessor) {
        String userId = (String) headerAccessor.getSessionAttributes().get("userId");
        notificationWebSocketService.sendPrivateNotification(userId, message);
    }
}
