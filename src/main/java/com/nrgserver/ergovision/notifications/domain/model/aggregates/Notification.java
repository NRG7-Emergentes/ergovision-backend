package com.nrgserver.ergovision.notifications.domain.model.aggregates;

import com.nrgserver.ergovision.notifications.domain.model.valueobjects.DeliveryChannel;
import com.nrgserver.ergovision.notifications.domain.model.valueobjects.NotificationStatus;
import com.nrgserver.ergovision.notifications.domain.model.valueobjects.NotificationType;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Objects;

@Getter
public class Notification {
    // Explicit getters used by the assembler (typed)
    // fields (adjusted)
    private Long id;
    private Long userId;
    private String title;
    private String message;
    private NotificationType type;
    private NotificationStatus status;
    // explicit setter for channel (avoid Lombok)
    // allow changing channel when preferences require it
    @Setter
    private DeliveryChannel channel;
    // timestamps as Instants (explicit)
    private Instant createdAt;
    private Instant readAt;

    private Notification() {
        // for factory
    }

    public static Notification create(Long id, Long userId, String title, String message, NotificationType type, DeliveryChannel channel) {
        Notification n = new Notification();
        n.id = id;
        n.userId = userId;
        n.title = title;
        n.message = message;
        n.type = type;
        n.channel = channel;
        n.createdAt = Instant.now();
        n.status = NotificationStatus.PENDING;
        return n;
    }

    public void send() {
        // Regla simple de dominio: título y tipo deben existir para intentar envío.
        if (Objects.isNull(type) || title == null || title.trim().isEmpty()) {
            this.status = NotificationStatus.FAILED;
            return;
        }
        // Simulación de envío: en la capa de infraestructura se haría el envío real.
        this.status = NotificationStatus.SENT;
        // optionally record a sent timestamp if needed (not required by assembler)
    }

    public void markAsRead() {
        this.status = NotificationStatus.READ;
        this.readAt = Instant.now();
    }

    public boolean isRead() {
        return this.status == NotificationStatus.READ;
    }

    public void resend() {
        if (this.status != NotificationStatus.FAILED) {
            throw new IllegalStateException("Only FAILED notifications can be resent");
        }
        this.status = NotificationStatus.PENDING;
        send();
    }

    public void markFailed() {
        this.status = NotificationStatus.FAILED;
    }

}