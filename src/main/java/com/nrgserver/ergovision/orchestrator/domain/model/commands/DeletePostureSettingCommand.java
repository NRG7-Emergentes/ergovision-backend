package com.nrgserver.ergovision.orchestrator.domain.model.commands;

/**
 * Command: DeletePostureSettingCommand
 * Deletes a posture setting configuration.
 */
public record DeletePostureSettingCommand(
        Long settingId
) {
}
