package com.nrgserver.ergovision.notifications.interfaces.rest;

import com.nrgserver.ergovision.notifications.domain.model.aggregates.Notification;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationWebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // 👇 Para recibir mensajes desde el frontend (si los necesitas)
    @MessageMapping("/notify")
    public void receiveNotification(Notification notification) {
        System.out.println("[WS] Notificación recibida desde frontend: " + notification.getTitle());
        // Broadcast a todos los clientes
        messagingTemplate.convertAndSend("/topic/notifications", notification);
    }

    // 👇 Método que puedes llamar desde tus command services
    public void sendNotification(Notification notification) {
        messagingTemplate.convertAndSend("/topic/notifications", notification);
    }
}
