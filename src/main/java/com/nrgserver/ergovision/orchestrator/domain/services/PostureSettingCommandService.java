package com.nrgserver.ergovision.orchestrator.domain.services;

import com.nrgserver.ergovision.orchestrator.domain.model.commands.CreatePostureSettingCommand;
import com.nrgserver.ergovision.orchestrator.domain.model.commands.DeletePostureSettingCommand;
import com.nrgserver.ergovision.orchestrator.domain.model.commands.UpdatePostureSettingCommand;

public interface PostureSettingCommandService {
    
    Long handle(CreatePostureSettingCommand command);
    
    Long handle(UpdatePostureSettingCommand command);
    
    void handle(DeletePostureSettingCommand command);
}
