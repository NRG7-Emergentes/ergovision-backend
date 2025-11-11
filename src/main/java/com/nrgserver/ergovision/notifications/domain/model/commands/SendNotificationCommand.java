package com.nrgserver.ergovision.notifications.domain.model.commands;

import java.util.List;

public record SendNotificationCommand(
    Long id,
    Long userId,
    String title,
    String message,
    String type, // INFO/REMINDER/ALERT
    String channel, // PUSH/SMS
    List<String> preferredChannels,
    boolean doNotDisturb
) {}
