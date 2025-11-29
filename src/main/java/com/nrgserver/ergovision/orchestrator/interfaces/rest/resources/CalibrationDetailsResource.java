package com.nrgserver.ergovision.orchestrator.interfaces.rest.resources;

public record CalibrationDetailsResource(
        Long id,
        Long userId,
        Long calibrationScore,
        Long cameraDistance,
        Long cameraVisibility,
        Long shoulderAngle,
        Long headAngle,
        String calibratedAt
) {
}
