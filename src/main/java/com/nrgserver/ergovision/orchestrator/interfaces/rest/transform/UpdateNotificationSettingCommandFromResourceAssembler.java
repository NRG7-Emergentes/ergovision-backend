package com.nrgserver.ergovision.orchestrator.interfaces.rest.transform;

import com.nrgserver.ergovision.orchestrator.domain.model.commands.UpdateNotificationSettingCommand;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.resources.UpdateNotificationSettingResource;

public class UpdateNotificationSettingCommandFromResourceAssembler {
    public static UpdateNotificationSettingCommand toCommandFromResource(Long notificationSettingId, UpdateNotificationSettingResource resource){
        return new UpdateNotificationSettingCommand(
                notificationSettingId,
                resource.emailNotifications()
        );
    }
}
