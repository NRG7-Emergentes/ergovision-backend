package com.nrgserver.ergovision.orchestrator.domain.model.commands;

public record UpdateAlertsSettingCommand(
        Long alertSettingId,
        Boolean visualAlertsEnabled,
        Boolean soundAlertsEnabled,
        Integer alertVolume,
        Integer pauseInterval
) {
}
