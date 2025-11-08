package com.nrgserver.ergovision.notifications.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "notifications_sent")
public class NotificationSent {

    // Getters y Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String title;
    private String message;
    private String type;
    private String channel;
    private LocalDateTime sentAt;
    private boolean success;

    public NotificationSent() {}

    public NotificationSent(Long userId, String title, String message, String type, String channel, boolean success) {
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.type = type;
        this.channel = channel;
        this.success = success;
        this.sentAt = LocalDateTime.now();
    }

}
