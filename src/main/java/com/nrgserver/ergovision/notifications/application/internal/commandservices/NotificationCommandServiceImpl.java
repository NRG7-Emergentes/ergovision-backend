package com.nrgserver.ergovision.notifications.application.internal.commandservices;

import com.nrgserver.ergovision.notifications.domain.model.aggregates.Notification;
import com.nrgserver.ergovision.notifications.domain.model.aggregates.UserPreferences;
import com.nrgserver.ergovision.notifications.domain.model.valueobjects.DeliveryChannel;
import com.nrgserver.ergovision.notifications.domain.model.valueobjects.NotificationType;
import com.nrgserver.ergovision.notifications.domain.services.NotificationService;
import com.nrgserver.ergovision.notifications.infrastructure.infrastructure.persistenence.jpa.repositories.NotificationRepository;

// importar los records de commands
import com.nrgserver.ergovision.notifications.domain.model.commands.SendNotificationCommand;
import com.nrgserver.ergovision.notifications.domain.model.commands.MarkNotificationAsReadCommand;
import com.nrgserver.ergovision.notifications.domain.model.commands.ResendNotificationCommand;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationCommandServiceImpl {
    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;

    public NotificationCommandServiceImpl(NotificationService notificationService, NotificationRepository notificationRepository) {
        this.notificationService = notificationService;
        this.notificationRepository = notificationRepository;
    }

    // Handle send: crea agregados y delega al domain service
    // ahora devuelve el id creado para que el controller lo consulte
    public Long handle(SendNotificationCommand cmd) {
        // no mutamos el record; calculamos el id localmente
        Long id = cmd.id() == null ? System.currentTimeMillis() : cmd.id();

        NotificationType t = null;
        try { t = cmd.type() != null ? NotificationType.valueOf(cmd.type()) : null; } catch (Exception ignored) {}
        DeliveryChannel ch = null;
        try { ch = cmd.channel() != null ? DeliveryChannel.valueOf(cmd.channel()) : null; } catch (Exception ignored) {}

        Notification n = Notification.create(id, cmd.userId(), cmd.title(), cmd.message(), t, ch);

        UserPreferences up = new UserPreferences(cmd.userId(),
                cmd.preferredChannels() == null ? null : cmd.preferredChannels().stream().map(s -> {
                    try { return DeliveryChannel.valueOf(s); } catch (Exception ex) { return null; }
                }).filter(java.util.Objects::nonNull).collect(Collectors.toList()),
                null,
                cmd.doNotDisturb());

        notificationService.sendNotification(n, up);
        // persist handled by domain service already, but ensure saved
        notificationRepository.save(n);

        return id;
    }

    // Handle mark as read
    public void handle(MarkNotificationAsReadCommand cmd) {
        notificationService.markAsRead(cmd.notificationId());
    }

    // Handle resend
    public void handle(ResendNotificationCommand cmd) {
        notificationService.retryDelivery(cmd.notificationId());
    }
}