package com.nrgserver.ergovision.notifications.application.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationWebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Procesa mensajes entrantes (solo echo de prueba)
     */
    public String processIncomingMessage(String message) {
        return "Echo: " + message;
    }

    /**
     * Envía una notificación a todos los suscriptores (broadcast)
     * ⚠️ Solo si quieres notificar globalmente
     */
    public void broadcastNotification(Object payload) {
        messagingTemplate.convertAndSend("/topic/all", payload);
    }

    /**
     * Envía una notificación privada a un usuario específico
     */
    public void sendPrivateNotification(String userId, String notification) {
        // Usamos /queue/notifications para que cada usuario reciba solo sus mensajes
        messagingTemplate.convertAndSendToUser(
                userId,                  // id del usuario (string)
                "/queue/notifications",  // canal privado
                notification             // payload
        );
    }
}
