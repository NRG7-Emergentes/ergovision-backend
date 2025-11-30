package com.nrgserver.ergovision.orchestrator.domain.model.commands;

public record CreateCalibrationDetailsCommand(
        Long userId,
        Long calibrationScore,
        Long cameraDistance,
        Long cameraVisibility,
        Long shoulderAngle,
        Long headAngle
) {
}
