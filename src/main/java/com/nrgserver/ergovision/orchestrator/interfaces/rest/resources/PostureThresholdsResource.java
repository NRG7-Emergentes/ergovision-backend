package com.nrgserver.ergovision.orchestrator.interfaces.rest.resources;

/**
 * Resource: PostureThresholdsResource
 * Represents posture thresholds data in REST responses.
 */
public record PostureThresholdsResource(
        Integer shoulderAngle,
        Integer neckAngle,
        Integer backAngle,
        Integer headTilt
) {
}
