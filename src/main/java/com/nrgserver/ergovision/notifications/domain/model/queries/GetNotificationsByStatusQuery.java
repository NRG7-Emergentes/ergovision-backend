package com.nrgserver.ergovision.notifications.domain.model.queries;

import com.nrgserver.ergovision.notifications.domain.model.valueobjects.NotificationStatus;

public record GetNotificationsByStatusQuery(Long userId, NotificationStatus status) {}
