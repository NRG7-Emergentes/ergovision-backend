package com.nrgserver.ergovision.orchestrator.domain.model.commands;

public record UpdateAlertsSettingCommand(
        Long settingId,
        Boolean visualAlertsEnabled,
        Boolean soundAlertsEnabled,
        Integer alertVolume,
        Integer alertInterval
) {
}
