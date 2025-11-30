package com.nrgserver.ergovision.orchestrator.interfaces.rest.resources;

public record NotificationSettingResource(
        Long id,
        Long userId,
        Boolean emailNotifications
) {
}
