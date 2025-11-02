package com.nrgserver.ergovision.orchestrator.domain.model.commands;

import com.nrgserver.ergovision.orchestrator.domain.model.valueobjects.PostureThresholds;

public record CreatePostureSettingCommand(
        Long userId,
        Integer postureSensitivity,
        Integer shoulderAngleThreshold,
        Integer headAngleThreshold,
        Integer samplingFrequency,
        Boolean showSkeleton,
        PostureThresholds postureThresholds
) {
}
