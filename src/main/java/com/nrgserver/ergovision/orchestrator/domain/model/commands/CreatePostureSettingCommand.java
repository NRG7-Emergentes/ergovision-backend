package com.nrgserver.ergovision.orchestrator.domain.model.commands;

public record CreatePostureSettingCommand(
        Long userId,
        Integer postureSensitivity,
        Integer samplingFrequency,
        Boolean showSkeleton
) {
}
