package com.nrgserver.ergovision.orchestrator.domain.model.commands;

public record CreateNotificationSettingCommand(Long userId, Boolean emailNotifications) {
}
