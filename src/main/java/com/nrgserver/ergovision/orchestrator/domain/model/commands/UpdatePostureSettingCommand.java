package com.nrgserver.ergovision.orchestrator.domain.model.commands;

import com.nrgserver.ergovision.orchestrator.domain.model.valueobjects.PostureThresholds;

/**
 * Command: UpdatePostureSettingCommand
 * Updates an existing posture setting configuration.
 */
public record UpdatePostureSettingCommand(
        Long settingId,
        Integer postureSensitivity,
        Integer shoulderAngleThreshold,
        Integer headAngleThreshold,
        Integer samplingFrequency,
        Boolean showSkeleton,
        PostureThresholds postureThresholds
) {
}
