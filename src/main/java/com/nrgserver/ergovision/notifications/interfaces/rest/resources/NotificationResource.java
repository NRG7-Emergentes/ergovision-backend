package com.nrgserver.ergovision.notifications.interfaces.rest.resources;

import java.time.Instant;

public record NotificationResource(
        Long id,
        Long userId,
        String title,
        String message,
        String type,
        String channel,
        String status,
        Instant createdAt,
        Instant readAt
) {
}
