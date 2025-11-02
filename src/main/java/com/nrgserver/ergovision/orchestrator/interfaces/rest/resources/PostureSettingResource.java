package com.nrgserver.ergovision.orchestrator.interfaces.rest.resources;

/**
 * Resource: PostureSettingResource
 * Represents posture setting data in REST responses.
 */
public record PostureSettingResource(
        Long id,
        Long userId,
        Integer postureSensitivity,
        Integer shoulderAngleThreshold,
        Integer headAngleThreshold,
        Integer samplingFrequency,
        Boolean showSkeleton,
        PostureThresholdsResource postureThresholds
) {
}
