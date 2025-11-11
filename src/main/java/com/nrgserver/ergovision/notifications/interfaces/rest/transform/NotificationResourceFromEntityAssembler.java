package com.nrgserver.ergovision.notifications.interfaces.rest.transform;

import com.nrgserver.ergovision.notifications.domain.model.aggregates.Notification;
import com.nrgserver.ergovision.notifications.interfaces.rest.resources.NotificationResource;

import java.time.Instant;

/*
 DDD: prefer explicit mapping from aggregate/read-model to resource.
 Reflection here was a temporary fallback; se reemplaza por getters explícitos.
*/

public class NotificationResourceFromEntityAssembler {

    public static NotificationResource toResourceFromEntity(Notification n) {
        if (n == null) return null;

        Long id = n.getId();
        Long userId = n.getUserId();
        String title = n.getTitle();
        String message = n.getMessage();
        String type = n.getType() != null ? n.getType().name() : null;
        String channel = n.getChannel() != null ? n.getChannel().name() : null;
        String status = n.getStatus() != null ? n.getStatus().name() : null;

        // Asumimos que el agregado expone Instant para timestamps. Si usa Date/Long, convierte aquí.
        Instant createdAt = n.getCreatedAt();
        Instant readAt = n.getReadAt();

        return new NotificationResource(
                id,
                userId,
                title,
                message,
                type,
                channel,
                status,
                createdAt,
                readAt
        );
    }
}