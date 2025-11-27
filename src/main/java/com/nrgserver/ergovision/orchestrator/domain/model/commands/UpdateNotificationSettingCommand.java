package com.nrgserver.ergovision.orchestrator.domain.model.commands;

public record UpdateNotificationSettingCommand(Long notificationSettingId, Boolean emailNotifications) {
}
