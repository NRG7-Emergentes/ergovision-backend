package com.nrgserver.ergovision.orchestrator.domain.services;

import com.nrgserver.ergovision.orchestrator.domain.model.commands.CreateNotificationSettingCommand;
import com.nrgserver.ergovision.orchestrator.domain.model.commands.DeleteNotificationSettingCommand;
import com.nrgserver.ergovision.orchestrator.domain.model.commands.UpdateNotificationSettingCommand;

public interface NotificationSettingCommandService {
    Long handle(CreateNotificationSettingCommand command);

    Long handle(UpdateNotificationSettingCommand command);

    void handle(DeleteNotificationSettingCommand command);
}
