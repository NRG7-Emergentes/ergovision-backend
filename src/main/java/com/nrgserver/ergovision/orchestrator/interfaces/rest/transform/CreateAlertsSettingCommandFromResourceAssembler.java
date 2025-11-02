package com.nrgserver.ergovision.orchestrator.interfaces.rest.transform;

import com.nrgserver.ergovision.orchestrator.domain.model.commands.CreateAlertsSettingCommand;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.resources.CreateAlertSettingResource;

/**
 * Transformer: CreateAlertsSettingCommandFromResourceAssembler
 * Converts CreateAlertSettingResource to CreateAlertsSettingCommand.
 */
public class CreateAlertsSettingCommandFromResourceAssembler {
    
    public static CreateAlertsSettingCommand toCommandFromResource(CreateAlertSettingResource resource) {
        return new CreateAlertsSettingCommand(
                resource.userId(),
                resource.visualAlertsEnabled(),
                resource.soundAlertsEnabled(),
                resource.alertVolume(),
                resource.alertInterval()
        );
    }
}
