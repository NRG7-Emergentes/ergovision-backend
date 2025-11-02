package com.nrgserver.ergovision.orchestrator.interfaces.rest.transform;

import com.nrgserver.ergovision.orchestrator.domain.model.commands.UpdateAlertsSettingCommand;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.resources.UpdateAlertSettingResource;

public class UpdateAlertsSettingCommandFromResourceAssembler {
    
    public static UpdateAlertsSettingCommand toCommandFromResource(Long settingId, UpdateAlertSettingResource resource) {
        return new UpdateAlertsSettingCommand(
                settingId,
                resource.visualAlertsEnabled(),
                resource.soundAlertsEnabled(),
                resource.alertVolume(),
                resource.alertInterval()
        );
    }
}
