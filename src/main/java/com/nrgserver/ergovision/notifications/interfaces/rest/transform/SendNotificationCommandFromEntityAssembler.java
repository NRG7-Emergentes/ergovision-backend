package com.nrgserver.ergovision.notifications.interfaces.rest.transform;

import com.nrgserver.ergovision.notifications.domain.model.commands.CreateNotificationCommand;
import com.nrgserver.ergovision.notifications.domain.model.commands.SendNotificationCommand;

public class SendNotificationCommandFromEntityAssembler {
    public static SendNotificationCommand toCommand(CreateNotificationCommand src) {
        return new SendNotificationCommand(
                null,
                src.userId(),
                src.title(),
                src.message(),
                src.type(),
                src.channel(),
                src.preferredChannels(),
                src.doNotDisturb()
        );
    }
}
