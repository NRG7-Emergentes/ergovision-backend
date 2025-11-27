package com.nrgserver.ergovision.orchestrator.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;

public record CreateNotificationSettingResource(
        @NotNull(message = "User ID is required")
        Long userId,

        @NotNull(message = "Email notification enabled flag is required")
        Boolean emailNotifications
) {
}
