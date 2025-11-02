package com.nrgserver.ergovision.orchestrator.domain.model.commands;

/**
 * Command: DeleteAlertsSettingCommand
 * Deletes an alert setting configuration.
 */
public record DeleteAlertsSettingCommand(
        Long settingId
) {
}
