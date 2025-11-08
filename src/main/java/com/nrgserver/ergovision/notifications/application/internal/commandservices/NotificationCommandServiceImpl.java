package com.nrgserver.ergovision.notifications.application.internal.commandservices;

import com.nrgserver.ergovision.notifications.application.websocket.NotificationWebSocketService;
import com.nrgserver.ergovision.notifications.domain.model.aggregates.Notification;
import com.nrgserver.ergovision.notifications.domain.model.aggregates.NotificationSent;
import com.nrgserver.ergovision.notifications.domain.model.aggregates.UserPreferences;
import com.nrgserver.ergovision.notifications.domain.model.valueobjects.DeliveryChannel;
import com.nrgserver.ergovision.notifications.domain.model.valueobjects.NotificationType;
import com.nrgserver.ergovision.notifications.domain.services.NotificationService;
import com.nrgserver.ergovision.notifications.infrastructure.persistence.jpa.repositories.NotificationRepository;
import com.nrgserver.ergovision.notifications.domain.model.commands.SendNotificationCommand;
import com.nrgserver.ergovision.notifications.domain.model.commands.MarkNotificationAsReadCommand;
import com.nrgserver.ergovision.notifications.domain.model.commands.ResendNotificationCommand;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

import com.nrgserver.ergovision.notifications.infrastructure.persistence.jpa.repositories.NotificationSentRepository;

@Service
public class NotificationCommandServiceImpl {
    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;
    private final NotificationWebSocketService webSocketService;

    @Autowired
    private NotificationSentRepository sentRepository;

    public NotificationCommandServiceImpl(NotificationService notificationService,
                                          NotificationRepository notificationRepository,
                                          NotificationWebSocketService webSocketService) {
        this.notificationService = notificationService;
        this.notificationRepository = notificationRepository;
        this.webSocketService = webSocketService;
    }

    @Transactional
    public Long handle(SendNotificationCommand cmd) {
        try {
            NotificationType t = null;
            try { t = cmd.type() != null ? NotificationType.valueOf(cmd.type()) : null; } catch (Exception ignored) {}
            DeliveryChannel ch = null;
            try { ch = cmd.channel() != null ? DeliveryChannel.valueOf(cmd.channel()) : null; } catch (Exception ignored) {}

            Notification n = Notification.create(
                    null,
                    cmd.userId(),
                    cmd.title(),
                    cmd.message(),
                    t,
                    ch
            );

            UserPreferences up = new UserPreferences(
                    cmd.userId(),
                    cmd.preferredChannels() == null ? null :
                            cmd.preferredChannels().stream()
                                    .map(s -> {
                                        try { return DeliveryChannel.valueOf(s); }
                                        catch (Exception ex) { return null; }
                                    })
                                    .filter(java.util.Objects::nonNull)
                                    .collect(Collectors.toList()),
                    null,
                    cmd.doNotDisturb()
            );

            notificationService.sendNotification(n, up);
            notificationRepository.save(n);

            webSocketService.sendPrivateMessage(String.valueOf(cmd.userId()), n);

            NotificationSent sentRecord = new NotificationSent(
                    n.getUserId(),
                    n.getTitle(),
                    n.getMessage(),
                    n.getType() != null ? n.getType().name() : null,
                    n.getChannel() != null ? n.getChannel().name() : null,
                    true
            );
            sentRepository.save(sentRecord);

            return n.getId(); // ✅ ahora devuelve el ID asignado por el repositorio
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("No se pudo crear la notificación: " + ex.getMessage());
        }
    }

    public void handle(MarkNotificationAsReadCommand cmd) {
        notificationService.markAsRead(cmd.notificationId());
    }

    public void handle(ResendNotificationCommand cmd) {
        notificationService.retryDelivery(cmd.notificationId());
    }
}
