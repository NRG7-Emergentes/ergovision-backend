package com.nrgserver.ergovision.orchestrator.domain.model.commands;

public record UpdatePostureSettingCommand(
        Long postureSettingId,
        Integer postureSensitivity,
        Integer samplingFrequency,
        Boolean showSkeleton
) {
}
