package com.nrgserver.ergovision.notifications.application.events;

import com.nrgserver.ergovision.notifications.application.internal.commandservices.NotificationCommandServiceImpl;
import com.nrgserver.ergovision.notifications.domain.model.commands.SendNotificationCommand;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class TaskAssignedEventHandler {
    private final NotificationCommandServiceImpl commandService;

    public TaskAssignedEventHandler(NotificationCommandServiceImpl commandService) {
        this.commandService = commandService;
    }

    public void handleTaskAssigned(Long taskId, Long assignedUserId, String taskTitle) {
        SendNotificationCommand cmd = new SendNotificationCommand(
            System.currentTimeMillis(),
            assignedUserId,
            "Tarea asignada: " + taskTitle,
            "Se te ha asignado la tarea " + taskTitle + " (id: " + taskId + ")",
            "REMINDER",
            "PUSH",
            Collections.singletonList("PUSH"),
            false
        );
        commandService.handle(cmd);
    }
}
