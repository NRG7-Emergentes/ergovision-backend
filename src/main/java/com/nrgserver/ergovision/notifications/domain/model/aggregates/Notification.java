package com.nrgserver.ergovision.notifications.domain.model.aggregates;

import com.nrgserver.ergovision.notifications.domain.model.valueobjects.DeliveryChannel;
import com.nrgserver.ergovision.notifications.domain.model.valueobjects.NotificationStatus;
import com.nrgserver.ergovision.notifications.domain.model.valueobjects.NotificationType;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Objects;

/**
 * Aggregate Root: Notification
 * Representa una notificación dirigida a un usuario.
 */
@Getter
public class Notification {
    // === Core attributes ===
    private Long id;
    private Long userId;
    private String title;
    private String message;
    private NotificationType type;
    private NotificationStatus status;

    // === Value Objects ===
    @Setter
    private DeliveryChannel channel;

    // === Metadata ===
    private Instant createdAt;
    private Instant readAt;

    // Constructor privado (factory pattern)
    private Notification() {}

    /**
     * Fábrica de notificaciones: asegura consistencia al crearlas.
     */
    public static Notification create(Long id, Long userId, String title, String message,
                                      NotificationType type, DeliveryChannel channel) {
        Notification n = new Notification();
        n.id = (id != null) ? id : System.currentTimeMillis(); // fallback simple
        n.userId = Objects.requireNonNull(userId, "UserId cannot be null");
        n.title = Objects.requireNonNullElse(title, "Untitled Notification");
        n.message = Objects.requireNonNullElse(message, "");
        n.type = (type != null) ? type : NotificationType.INFO;
        n.channel = (channel != null) ? channel : DeliveryChannel.PUSH;
        n.createdAt = Instant.now();
        n.status = NotificationStatus.PENDING;
        return n;
    }

    /**
     * Lógica de envío de la notificación.
     * (En la capa de infraestructura se implementaría el envío real)
     */
    public void send() {
        if (Objects.isNull(type) || title == null || title.trim().isEmpty()) {
            markFailed();
            return;
        }
        this.status = NotificationStatus.SENT;
    }

    /**
     * Marca la notificación como leída.
     */
    public void markAsRead() {
        if (this.status == NotificationStatus.READ) return; // idempotente
        this.status = NotificationStatus.READ;
        this.readAt = Instant.now();
    }

    public boolean isRead() {
        return this.status == NotificationStatus.READ;
    }

    /**
     * Permite reenviar una notificación.
     * Solo puede reenviarse si previamente falló o ya fue enviada.
     */
    public void resend() {
        if (this.status != NotificationStatus.FAILED && this.status != NotificationStatus.SENT) {
            throw new IllegalStateException(
                    "Notification cannot be resent from current state: " + this.status
            );
        }
        this.status = NotificationStatus.PENDING;
        send();
    }

    /**
     * Marca la notificación como fallida.
     */
    public void markFailed() {
        this.status = NotificationStatus.FAILED;
    }
}
