package com.nrgserver.ergovision.notifications.application.events;

import com.nrgserver.ergovision.notifications.application.internal.commandservices.NotificationCommandServiceImpl;
import com.nrgserver.ergovision.notifications.domain.model.commands.SendNotificationCommand;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class GroupJoinedEventHandler {
    private final NotificationCommandServiceImpl commandService;

    public GroupJoinedEventHandler(NotificationCommandServiceImpl commandService) {
        this.commandService = commandService;
    }

    public void handleGroupJoined(Long groupId, Long memberId, String groupName) {
        SendNotificationCommand cmd = new SendNotificationCommand(
            System.currentTimeMillis(),
            memberId,
            "Bienvenido al grupo " + groupName,
            "Has sido añadido al grupo " + groupName,
            "INFO",
            "PUSH",
            Collections.singletonList("PUSH"),
            false
        );
        commandService.handle(cmd);
    }
}
