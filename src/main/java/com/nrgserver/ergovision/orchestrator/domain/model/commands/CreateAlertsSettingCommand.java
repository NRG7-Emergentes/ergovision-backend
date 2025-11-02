package com.nrgserver.ergovision.orchestrator.domain.model.commands;

/**
 * Command: CreateAlertsSettingCommand
 * Creates a new alert setting configuration for a user.
 */
public record CreateAlertsSettingCommand(
        Long userId,
        Boolean visualAlertsEnabled,
        Boolean soundAlertsEnabled,
        Integer alertVolume,
        Integer alertInterval
) {
}
