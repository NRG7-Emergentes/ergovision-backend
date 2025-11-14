package com.nrgserver.ergovision.orchestrator.interfaces.rest.resources;

public record AlertSettingResource(
        Long id,
        Long userId,
        Boolean visualAlertsEnabled,
        Boolean soundAlertsEnabled,
        Integer alertVolume,
        Integer pauseInterval
) {
}
