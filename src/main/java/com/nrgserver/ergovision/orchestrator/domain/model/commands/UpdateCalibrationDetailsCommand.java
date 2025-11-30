package com.nrgserver.ergovision.orchestrator.domain.model.commands;

public record UpdateCalibrationDetailsCommand(
        Long calibrationDetailsId,
        Long calibrationScore,
        Long cameraDistance,
        Long cameraVisibility,
        Long shoulderAngle,
        Long headAngle
) {
}
