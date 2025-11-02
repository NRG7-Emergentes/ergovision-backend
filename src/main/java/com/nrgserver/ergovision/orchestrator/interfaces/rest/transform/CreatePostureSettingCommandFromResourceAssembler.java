package com.nrgserver.ergovision.orchestrator.interfaces.rest.transform;

import com.nrgserver.ergovision.orchestrator.domain.model.commands.CreatePostureSettingCommand;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.resources.CreatePostureSettingResource;

/**
 * Transformer: CreatePostureSettingCommandFromResourceAssembler
 * Converts CreatePostureSettingResource to CreatePostureSettingCommand.
 */
public class CreatePostureSettingCommandFromResourceAssembler {
    
    public static CreatePostureSettingCommand toCommandFromResource(CreatePostureSettingResource resource) {
        return new CreatePostureSettingCommand(
                resource.userId(),
                resource.postureSensitivity(),
                resource.shoulderAngleThreshold(),
                resource.headAngleThreshold(),
                resource.samplingFrequency(),
                resource.showSkeleton(),
                PostureThresholdsResourceFromValueObjectAssembler.toValueObjectFromResource(
                        resource.postureThresholds()
                )
        );
    }
}
