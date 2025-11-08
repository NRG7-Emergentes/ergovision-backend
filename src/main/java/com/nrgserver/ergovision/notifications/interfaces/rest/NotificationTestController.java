package com.nrgserver.ergovision.notifications.interfaces.rest;

import com.nrgserver.ergovision.notifications.application.internal.commandservices.NotificationCommandServiceImpl;
import com.nrgserver.ergovision.notifications.domain.model.aggregates.Notification;
import com.nrgserver.ergovision.notifications.domain.model.commands.SendNotificationCommand;
import com.nrgserver.ergovision.notifications.domain.model.valueobjects.NotificationType;
import com.nrgserver.ergovision.notifications.domain.model.valueobjects.DeliveryChannel;
import com.nrgserver.ergovision.notifications.interfaces.rest.resources.NotificationResource;
import com.nrgserver.ergovision.notifications.interfaces.rest.transform.NotificationResourceFromEntityAssembler;
import com.nrgserver.ergovision.notifications.application.internal.queryservices.NotificationQueryServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/notifications/test")
public class NotificationTestController {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationTestController.class);

    private final NotificationCommandServiceImpl commandService;
    private final NotificationQueryServiceImpl queryService;

    public NotificationTestController(
            NotificationCommandServiceImpl commandService,
            NotificationQueryServiceImpl queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    /**
     * Endpoint temporal para verificar el envío de notificaciones globalmente.
     * Envía una notificación a todos los usuarios simulados (ID 1..3)
     */
    @PostMapping("/global")
    public ResponseEntity<Map<String, Object>> sendGlobalNotification(
            @RequestParam(defaultValue = "Test Global Notification") String title,
            @RequestParam(defaultValue = "Esta es una notificación de prueba global") String message
    ) {
        LOG.info("=== [TEST] Enviando notificación global de prueba ===");

        List<Long> simulatedUsers = List.of(1L, 2L, 3L);
        List<NotificationResource> sent = new ArrayList<>();

        for (Long userId : simulatedUsers) {
            SendNotificationCommand cmd = new SendNotificationCommand(
                    null,
                    userId,
                    title,
                    message,
                    NotificationType.SYSTEM.name(),
                    DeliveryChannel.PUSH.name(),
                    List.of("PUSH"),
                    false
            );
            Long id = commandService.handle(cmd);
            queryService.getById(id)
                    .map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
                    .ifPresent(sent::add);
        }

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "OK");
        response.put("total_sent", sent.size());
        response.put("notifications", sent);

        LOG.info("[TEST] {} notificaciones enviadas globalmente", sent.size());
        return ResponseEntity.ok(response);
    }
}
