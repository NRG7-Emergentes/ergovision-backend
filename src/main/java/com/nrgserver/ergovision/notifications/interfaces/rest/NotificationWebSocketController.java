package com.nrgserver.ergovision.notifications.interfaces.rest;

import com.nrgserver.ergovision.notifications.application.internal.commandservices.NotificationCommandServiceImpl;
import com.nrgserver.ergovision.notifications.application.internal.queryservices.NotificationQueryServiceImpl;
import com.nrgserver.ergovision.notifications.domain.model.aggregates.Notification;
import com.nrgserver.ergovision.notifications.domain.model.commands.CreateNotificationCommand;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationCommandServiceImpl commandService;
    private final NotificationQueryServiceImpl queryService;

    public NotificationWebSocketController(
            SimpMessagingTemplate messagingTemplate,
            NotificationCommandServiceImpl commandService,
            NotificationQueryServiceImpl queryService) {

        this.messagingTemplate = messagingTemplate;
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @MessageMapping("/notify")
    public void receiveNotification(CreateNotificationCommand command) {
        System.out.println("[WS] Recibido via STOMP: " + command.title());

        // 1. Guardar en BD
        Long id = commandService.handle(command);

        // 2. Obtener la notificación persistida
        queryService.getById(id).ifPresent(saved -> messagingTemplate.convertAndSend("/topic/notifications", saved));

    }
}
