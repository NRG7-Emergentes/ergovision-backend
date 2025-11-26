package com.nrgserver.ergovision.notifications.application.websocket;

import com.nrgserver.ergovision.notifications.domain.model.aggregates.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationWebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public String processIncomingMessage(String message) {
        return "Echo: " + message;
    }

    // Notifica a todos los suscriptores (broadcast)
    public void broadcastNotification(Object payload) {
        messagingTemplate.convertAndSend("/topic/all", payload);
    }

    // Envía una notificación a un usuario específico
    public void sendPrivateMessage(String userId, Object payload) {
        messagingTemplate.convertAndSendToUser(userId, "/queue/private", payload);
    }

}
