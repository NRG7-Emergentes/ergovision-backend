package com.nrgserver.ergovision.orchestrator.interfaces.rest.resources;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Resource: UpdateAlertSettingResource
 * Request body for updating alert settings.
 */
public record UpdateAlertSettingResource(
        @NotNull(message = "Visual alerts enabled flag is required")
        Boolean visualAlertsEnabled,
        
        @NotNull(message = "Sound alerts enabled flag is required")
        Boolean soundAlertsEnabled,
        
        @NotNull(message = "Alert volume is required")
        @Min(value = 0, message = "Alert volume must be between 0 and 100")
        @Max(value = 100, message = "Alert volume must be between 0 and 100")
        Integer alertVolume,
        
        @NotNull(message = "Alert interval is required")
        @Min(value = 1, message = "Alert interval must be at least 1 second")
        Integer alertInterval
) {
}
