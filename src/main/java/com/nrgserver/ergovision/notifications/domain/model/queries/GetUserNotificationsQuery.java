package com.nrgserver.ergovision.notifications.domain.model.queries;

import com.nrgserver.ergovision.notifications.domain.model.valueobjects.NotificationStatus;
import com.nrgserver.ergovision.notifications.domain.model.valueobjects.NotificationType;

public record GetUserNotificationsQuery(
        Long userId,
        NotificationStatus status,
        NotificationType type
) {}
