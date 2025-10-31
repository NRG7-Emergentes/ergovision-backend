package com.nrgserver.ergovision.notifications.interfaces.rest;

import com.nrgserver.ergovision.notifications.application.internal.commandservices.NotificationCommandServiceImpl;
import com.nrgserver.ergovision.notifications.application.internal.queryservices.NotificationQueryServiceImpl;
import com.nrgserver.ergovision.notifications.domain.model.queries.GetNotificationsByStatusQuery;
import com.nrgserver.ergovision.notifications.domain.model.queries.GetUnreadNotificationsQuery;
import com.nrgserver.ergovision.notifications.domain.model.queries.GetUserNotificationsQuery;
import com.nrgserver.ergovision.notifications.domain.model.commands.MarkNotificationAsReadCommand;
import com.nrgserver.ergovision.notifications.domain.model.commands.ResendNotificationCommand;
import com.nrgserver.ergovision.notifications.domain.model.commands.SendNotificationCommand;
import com.nrgserver.ergovision.notifications.domain.model.aggregates.Notification;
import com.nrgserver.ergovision.notifications.domain.model.commands.CreateNotificationCommand;
import com.nrgserver.ergovision.notifications.domain.model.valueobjects.NotificationStatus;
import com.nrgserver.ergovision.notifications.domain.model.valueobjects.NotificationType;
import com.nrgserver.ergovision.notifications.interfaces.rest.transform.SendNotificationCommandFromEntityAssembler;
import com.nrgserver.ergovision.notifications.interfaces.rest.resources.NotificationResource;
import com.nrgserver.ergovision.notifications.interfaces.rest.transform.NotificationResourceFromEntityAssembler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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

    // GET /api/v1/notifications/user/{userId}?status=&type=
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

    // GET /api/v1/notifications/unread/{userId}
    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<List<NotificationResource>> getUnreadNotifications(@PathVariable Long userId) {
        GetUnreadNotificationsQuery q = new GetUnreadNotificationsQuery(userId);
        List<Notification> list = queryService.handle(q);
        List<NotificationResource> resources = list.stream()
                .map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    // GET /api/v1/notifications/user/{userId}/status
    @GetMapping("/user/{userId}/status")
    public ResponseEntity<List<NotificationResource>> getNotificationsByStatus(
            @PathVariable Long userId,
            @RequestParam(name = "status") String status
    ) {
        NotificationStatus s = parseStatus(status);
        GetNotificationsByStatusQuery q = new GetNotificationsByStatusQuery(userId, s);
        List<Notification> list = queryService.handle(q);
        List<NotificationResource> resources = list.stream()
                .map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    // GET /api/v1/notifications/{notificationId}
    @GetMapping("/{notificationId}")
    public ResponseEntity<NotificationResource> getNotificationById(@PathVariable Long notificationId) {
        Optional<Notification> found = queryService.getById(notificationId);
        return found.map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // PATCH /api/v1/notifications/{notificationId}/read
    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long notificationId) {
        MarkNotificationAsReadCommand cmd = new MarkNotificationAsReadCommand(notificationId);
        commandService.handle(cmd);
        return ResponseEntity.noContent().build();
    }

    // POST /api/v1/notifications
    @PostMapping
    public ResponseEntity<NotificationResource> sendNotification(@RequestBody CreateNotificationCommand req) {
        SendNotificationCommand cmd = SendNotificationCommandFromEntityAssembler.toCommand(req);
        Long createdId = commandService.handle(cmd);

        Optional<Notification> created = queryService.getById(createdId);
        return created.map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
                .map(nr -> ResponseEntity.status(HttpStatus.CREATED).body(nr))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    // PATCH /api/v1/notifications/{notificationId}/resend
    @PatchMapping("/{notificationId}/resend")
    public ResponseEntity<Void> resendNotification(@PathVariable Long notificationId) {
        ResendNotificationCommand cmd = new ResendNotificationCommand(notificationId);
        commandService.handle(cmd);
        return ResponseEntity.noContent().build();
    }

    // Helpers
    private NotificationStatus parseStatus(String s) {
        if (s == null) return null;
        try { return NotificationStatus.valueOf(s); } catch (Exception ex) { return null; }
    }

    private NotificationType parseType(String s) {
        if (s == null) return null;
        try { return NotificationType.valueOf(s); } catch (Exception ex) { return null; }
    }
}
