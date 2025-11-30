package com.nrgserver.ergovision.orchestrator.interfaces.rest.resources;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record UpdateCalibrationDetailsResource(
        @Min(value = 0, message = "Calibration score must be non-negative")
        @Max(value = 100, message = "Calibration score must be at most 100")
        Long calibrationScore,
        Long cameraDistance,
        @Min(value = 0, message = "Camera visibility must be non-negative")
        @Max(value = 100, message = "Camera visibility must be at most 100")
        Long cameraVisibility,
        @Min(value = -180, message = "Shoulder angle must be at least -180")
        @Max(value = 180, message = "Shoulder angle must be at most 180")
        Long shoulderAngle,
        @Min(value = -180, message = "Head angle must be at least -180")
        @Max(value = 180, message = "Head angle must be at most 180")
        Long headAngle
) {
}
