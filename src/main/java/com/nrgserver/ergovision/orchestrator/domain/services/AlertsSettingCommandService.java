package com.nrgserver.ergovision.orchestrator.domain.services;

import com.nrgserver.ergovision.orchestrator.domain.model.commands.CreateAlertsSettingCommand;
import com.nrgserver.ergovision.orchestrator.domain.model.commands.DeleteAlertsSettingCommand;
import com.nrgserver.ergovision.orchestrator.domain.model.commands.UpdateAlertsSettingCommand;

public interface AlertsSettingCommandService {
    
    Long handle(CreateAlertsSettingCommand command);
    
    Long handle(UpdateAlertsSettingCommand command);
    
    void handle(DeleteAlertsSettingCommand command);
}
