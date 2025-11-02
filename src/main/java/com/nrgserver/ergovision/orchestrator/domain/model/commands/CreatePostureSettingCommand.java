package com.nrgserver.ergovision.orchestrator.domain.model.commands;

import com.nrgserver.ergovision.orchestrator.domain.model.valueobjects.PostureThresholds;

/**
 * Command: CreatePostureSettingCommand
 * Creates a new posture setting configuration for a user.
 */
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
