package com.nrgserver.ergovision.orchestrator.interfaces.rest.resources;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateCalibrationDetailsResource(
        @NotNull(message = "User ID is required")
        Long userId,
        @NotNull(message = "Calibration score is required")
        @Min(value = 0, message = "Calibration score must be non-negative")
        @Max(value = 100, message = "Calibration score must be at most 100")
        Long calibrationScore,
        @NotNull(message = "Camera distance is required")
        Long cameraDistance,
        @NotNull(message = "Camera visibility is required")
        @Min(value = 0, message = "Camera visibility must be non-negative")
        @Max(value = 100, message = "Camera visibility must be at most 100")
        Long cameraVisibility,
        @NotNull(message = "Shoulder angle is required")
        @Min(value = -180, message = "Shoulder angle must be at least -180")
        @Max(value = 180, message = "Shoulder angle must be at most 180")
        Long shoulderAngle,
        @NotNull(message = "Head angle is required")
        @Min(value = -180, message = "Head angle must be at least -180")
        @Max(value = 180, message = "Head angle must be at most 180")
        Long headAngle
) {
}
