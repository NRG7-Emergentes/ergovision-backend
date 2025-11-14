package com.nrgserver.ergovision.orchestrator.interfaces.rest.transform;

import com.nrgserver.ergovision.orchestrator.domain.model.commands.UpdatePostureSettingCommand;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.resources.UpdatePostureSettingResource;

public class UpdatePostureSettingCommandFromResourceAssembler {
    
    public static UpdatePostureSettingCommand toCommandFromResource(Long settingId, UpdatePostureSettingResource resource) {
        return new UpdatePostureSettingCommand(
                settingId,
                resource.postureSensitivity(),
                resource.samplingFrequency(),
                resource.showSkeleton()
        );
    }
}
