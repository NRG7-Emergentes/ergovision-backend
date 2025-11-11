package com.nrgserver.ergovision.orchestrator.domain.model.commands;

public record CreateAlertsSettingCommand(
        Long userId,
        Boolean visualAlertsEnabled,
        Boolean soundAlertsEnabled,
        Integer alertVolume,
        Integer alertInterval
) {
}
