package com.nrgserver.ergovision.orchestrator.interfaces.rest.resources;

public record PostureThresholdsResource(
        Integer shoulderAngle,
        Integer neckAngle,
        Integer backAngle,
        Integer headTilt
) {
}
