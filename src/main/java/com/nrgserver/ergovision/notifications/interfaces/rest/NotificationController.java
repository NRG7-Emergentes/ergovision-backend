package com.nrgserver.ergovision.notifications.interfaces.rest;

import com.nrgserver.ergovision.notifications.application.internal.commandservices.NotificationCommandServiceImpl;
import com.nrgserver.ergovision.notifications.application.internal.queryservices.NotificationQueryServiceImpl;
import com.nrgserver.ergovision.notifications.domain.model.aggregates.Notification;
import com.nrgserver.ergovision.notifications.domain.model.commands.CreateNotificationCommand;
import com.nrgserver.ergovision.notifications.domain.model.commands.MarkNotificationAsReadCommand;
import com.nrgserver.ergovision.notifications.domain.model.commands.ResendNotificationCommand;
import com.nrgserver.ergovision.notifications.domain.model.commands.SendNotificationCommand;
import com.nrgserver.ergovision.notifications.domain.model.queries.GetNotificationsByStatusQuery;
import com.nrgserver.ergovision.notifications.domain.model.queries.GetUnreadNotificationsQuery;
import com.nrgserver.ergovision.notifications.domain.model.queries.GetUserNotificationsQuery;
import com.nrgserver.ergovision.notifications.domain.model.valueobjects.NotificationStatus;
import com.nrgserver.ergovision.notifications.domain.model.valueobjects.NotificationType;
import com.nrgserver.ergovision.notifications.interfaces.rest.resources.NotificationResource;
import com.nrgserver.ergovision.notifications.interfaces.rest.transform.NotificationResourceFromEntityAssembler;
import com.nrgserver.ergovision.notifications.interfaces.rest.transform.SendNotificationCommandFromEntityAssembler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    private final NotificationCommandServiceImpl commandService;
    private final NotificationQueryServiceImpl queryService;

    public NotificationController(NotificationCommandServiceImpl commandService, NotificationQueryServiceImpl queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    // ✅ NUEVO: Obtener TODAS las notificaciones
    // GET /api/v1/notifications/all
    @GetMapping("/all")
    public ResponseEntity<List<NotificationResource>> getAllNotifications() {
        List<Notification> list = queryService.getAllNotifications();
        if (list.isEmpty()) return ResponseEntity.noContent().build();

        List<NotificationResource> resources = list.stream()
                .map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resources);
    }

    // ✅ EXISTENTE: Obtener notificaciones por usuario (con filtros)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResource>> getUserNotifications(
            @PathVariable Long userId,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "type", required = false) String type
    ) {
        NotificationStatus statusFilter = parseStatus(status);
        NotificationType typeFilter = parseType(type);

        GetUserNotificationsQuery q = new GetUserNotificationsQuery(userId, statusFilter, typeFilter);
        List<Notification> list = queryService.handle(q);

        List<NotificationResource> resources = list.stream()
                .map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resources);
    }

    // ✅ NUEVO: Obtener todas las notificaciones por estado (sin importar el usuario)
    // GET /api/v1/notifications/status?status=READ
    @GetMapping("/status")
    public ResponseEntity<List<NotificationResource>> getAllByStatus(
            @RequestParam(name = "status") String status
    ) {
        NotificationStatus s = parseStatus(status);
        if (s == null) return ResponseEntity.badRequest().build();

        List<Notification> list = queryService.findByStatus(s);
        if (list.isEmpty()) return ResponseEntity.noContent().build();

        List<NotificationResource> resources = list.stream()
                .map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resources);
    }

    // EXISTENTE: Obtener no leídas por usuario
    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<List<NotificationResource>> getUnreadNotifications(@PathVariable Long userId) {
        GetUnreadNotificationsQuery q = new GetUnreadNotificationsQuery(userId);
        List<Notification> list = queryService.handle(q);
        List<NotificationResource> resources = list.stream()
                .map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    // EXISTENTE: Obtener por ID
    @GetMapping("/{notificationId}")
    public ResponseEntity<NotificationResource> getNotificationById(@PathVariable Long notificationId) {
        Optional<Notification> found = queryService.getById(notificationId);
        return found.map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // ✅ CORREGIDO: Crear y enviar notificación
    // POST /api/v1/notifications
    @PostMapping
    public ResponseEntity<Map<String, Object>> sendNotification(@RequestBody CreateNotificationCommand req) {
        // Validación básica
        if (req.title() == null || req.title().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "ERROR",
                    "message", "El título de la notificación es obligatorio."
            ));
        }

        SendNotificationCommand cmd = SendNotificationCommandFromEntityAssembler.toCommand(req);
        Long createdId = commandService.handle(cmd);

        Optional<Notification> created = queryService.getById(createdId);
        if (created.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "ERROR", "message", "No se pudo crear la notificación."));
        }

        NotificationResource resource = NotificationResourceFromEntityAssembler.toResourceFromEntity(created.get());

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "OK");
        response.put("message", "Notificación creada y enviada correctamente.");
        response.put("notification", resource);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // EXISTENTE: Marcar como leída
    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long notificationId) {
        commandService.handle(new MarkNotificationAsReadCommand(notificationId));
        return ResponseEntity.noContent().build();
    }

    // EXISTENTE: Reenviar notificación
    @PatchMapping("/{notificationId}/resend")
    public ResponseEntity<Void> resendNotification(@PathVariable Long notificationId) {
        commandService.handle(new ResendNotificationCommand(notificationId));
        return ResponseEntity.noContent().build();
    }

    // Helpers
    private NotificationStatus parseStatus(String s) {
        if (s == null) return null;
        try { return NotificationStatus.valueOf(s.toUpperCase()); }
        catch (Exception ex) { return null; }
    }

    private NotificationType parseType(String s) {
        if (s == null) return null;
        try { return NotificationType.valueOf(s.toUpperCase()); }
        catch (Exception ex) { return null; }
    }
}
