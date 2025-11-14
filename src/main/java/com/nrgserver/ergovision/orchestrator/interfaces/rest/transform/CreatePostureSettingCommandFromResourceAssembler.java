package com.nrgserver.ergovision.orchestrator.interfaces.rest.transform;

import com.nrgserver.ergovision.orchestrator.domain.model.commands.CreatePostureSettingCommand;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.resources.CreatePostureSettingResource;

public class CreatePostureSettingCommandFromResourceAssembler {
    
    public static CreatePostureSettingCommand toCommandFromResource(CreatePostureSettingResource resource) {
        return new CreatePostureSettingCommand(
                resource.userId(),
                resource.postureSensitivity(),
                resource.samplingFrequency(),
                resource.showSkeleton()
        );
    }
}
