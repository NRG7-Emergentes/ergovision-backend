package com.nrgserver.ergovision.orchestrator.domain.model.commands;

/**
 * Command: UpdateAlertsSettingCommand
 * Updates an existing alert setting configuration.
 */
public record UpdateAlertsSettingCommand(
        Long settingId,
        Boolean visualAlertsEnabled,
        Boolean soundAlertsEnabled,
        Integer alertVolume,
        Integer alertInterval
) {
}
