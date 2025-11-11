package com.nrgserver.ergovision.notifications.domain.model.commands;

import java.util.List;

public record CreateNotificationCommand(
        Long userId,
        String title,
        String message,
        String type,
        String channel,
        List<String> preferredChannels,
        Boolean doNotDisturb
) {}
