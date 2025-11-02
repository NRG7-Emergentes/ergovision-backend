package com.nrgserver.ergovision.orchestrator.interfaces.rest.resources;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Resource: CreatePostureSettingResource
 * Request body for creating posture settings.
 */
public record CreatePostureSettingResource(
        @NotNull(message = "User ID is required")
        Long userId,
        
        @NotNull(message = "Posture sensitivity is required")
        @Min(value = 0, message = "Posture sensitivity must be between 0 and 100")
        @Max(value = 100, message = "Posture sensitivity must be between 0 and 100")
        Integer postureSensitivity,
        
        @NotNull(message = "Shoulder angle threshold is required")
        @Min(value = 0, message = "Shoulder angle threshold must be between 0 and 90")
        @Max(value = 90, message = "Shoulder angle threshold must be between 0 and 90")
        Integer shoulderAngleThreshold,
        
        @NotNull(message = "Head angle threshold is required")
        @Min(value = 0, message = "Head angle threshold must be between 0 and 90")
        @Max(value = 90, message = "Head angle threshold must be between 0 and 90")
        Integer headAngleThreshold,
        
        @NotNull(message = "Sampling frequency is required")
        @Min(value = 1, message = "Sampling frequency must be at least 1 second")
        Integer samplingFrequency,
        
        @NotNull(message = "Show skeleton flag is required")
        Boolean showSkeleton,
        
        @Valid
        PostureThresholdsResource postureThresholds
) {
}
