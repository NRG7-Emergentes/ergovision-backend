package com.nrgserver.ergovision.orchestrator.interfaces.rest.transform;

import com.nrgserver.ergovision.notifications.domain.model.commands.CreateNotificationCommand;
import com.nrgserver.ergovision.orchestrator.domain.model.commands.CreateNotificationSettingCommand;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.resources.CreateNotificationSettingResource;

public class CreateNotificationSettingCommandFromResourceAssembler {
    public static CreateNotificationSettingCommand toCommandFromResource(CreateNotificationSettingResource resource) {
        return new CreateNotificationSettingCommand(
                resource.userId(),
                resource.emailNotifications()
        );
    }
}
