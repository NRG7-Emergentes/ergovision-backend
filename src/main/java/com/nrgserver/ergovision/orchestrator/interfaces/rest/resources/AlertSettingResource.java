package com.nrgserver.ergovision.orchestrator.interfaces.rest.resources;

/**
 * Resource: AlertSettingResource
 * Represents alert setting data in REST responses.
 */
public record AlertSettingResource(
        Long id,
        Long userId,
        Boolean visualAlertsEnabled,
        Boolean soundAlertsEnabled,
        Integer alertVolume,
        Integer alertInterval
) {
}
