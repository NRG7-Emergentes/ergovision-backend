package com.nrgserver.ergovision.orchestrator.interfaces.rest.resources;

public record PostureSettingResource(
        Long id,
        Long userId,
        Integer postureSensitivity,
        Integer samplingFrequency,
        Boolean showSkeleton
) {
}
