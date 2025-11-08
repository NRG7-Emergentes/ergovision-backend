package com.nrgserver.ergovision.notifications.domain.model.aggregates;

import com.nrgserver.ergovision.notifications.domain.model.valueobjects.DeliveryChannel;
import com.nrgserver.ergovision.notifications.domain.model.valueobjects.NotificationStatus;
import com.nrgserver.ergovision.notifications.domain.model.valueobjects.NotificationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.Objects;

/**
 * Aggregate Root: Notification
 * Representa una notificación dirigida a un usuario.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {

    // === Identificador ===
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // === Core attributes ===
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationStatus status;

    // === Value Objects ===
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryChannel channel;

    // === Metadata ===
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "read_at")
    private Instant readAt;

    @Column(name = "sent_at")
    private Instant sentAt;

    @Column(nullable = false)
    private boolean success = false;

    // === Factory Method ===
    public static Notification create(Long id, Long userId, String title, String message,
                                      NotificationType type, DeliveryChannel channel) {
        Notification n = new Notification();
        n.id = id; // ✅ puede venir null
        n.userId = Objects.requireNonNull(userId, "UserId cannot be null");
        n.title = Objects.requireNonNullElse(title, "Untitled Notification");
        n.message = Objects.requireNonNullElse(message, "");
        n.type = (type != null) ? type : NotificationType.INFO;
        n.channel = (channel != null) ? channel : DeliveryChannel.PUSH;
        n.status = NotificationStatus.PENDING;
        return n;
    }


    // === Domain Behavior ===

    public void send() {
        if (Objects.isNull(type) || title == null || title.trim().isEmpty()) {
            markFailed();
            return;
        }
        this.status = NotificationStatus.SENT;
        this.sentAt = Instant.now();
        this.success = true;
    }

    public void markAsRead() {
        if (this.status == NotificationStatus.READ) return; // idempotente
        this.status = NotificationStatus.READ;
        this.readAt = Instant.now();
    }

    public boolean isRead() {
        return this.status == NotificationStatus.READ;
    }

    public void resend() {
        if (this.status != NotificationStatus.FAILED && this.status != NotificationStatus.SENT) {
            throw new IllegalStateException(
                    "Notification cannot be resent from current state: " + this.status
            );
        }
        this.status = NotificationStatus.PENDING;
        send();
    }

    public void markFailed() {
        this.status = NotificationStatus.FAILED;
        this.success = false;
    }
}
