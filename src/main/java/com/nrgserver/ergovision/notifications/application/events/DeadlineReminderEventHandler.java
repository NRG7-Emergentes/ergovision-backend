package com.nrgserver.ergovision.notifications.application.events;

import com.nrgserver.ergovision.notifications.application.internal.commandservices.NotificationCommandServiceImpl;
import com.nrgserver.ergovision.notifications.domain.model.commands.SendNotificationCommand;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class DeadlineReminderEventHandler {
    private final NotificationCommandServiceImpl commandService;

    public DeadlineReminderEventHandler(NotificationCommandServiceImpl commandService) {
        this.commandService = commandService;
    }

    public void handleDeadlineReminder(Long taskId, Long userId, String taskTitle) {
        SendNotificationCommand cmd = new SendNotificationCommand(
            System.currentTimeMillis(),
            userId,
            "Recordatorio: " + taskTitle,
            "La tarea " + taskTitle + " está próxima a vencer.",
            "REMINDER",
            "PUSH",
            Collections.singletonList("PUSH"),
            false
        );
        commandService.handle(cmd);
    }
}
