package com.nrgserver.ergovision.orchestrator.domain.services;

import com.nrgserver.ergovision.orchestrator.domain.model.commands.CreatePostureSettingCommand;
import com.nrgserver.ergovision.orchestrator.domain.model.commands.DeletePostureSettingCommand;
import com.nrgserver.ergovision.orchestrator.domain.model.commands.UpdatePostureSettingCommand;

/**
 * Service: PostureSettingCommandService
 * Manages write operations for posture settings.
 */
public interface PostureSettingCommandService {
    
    /**
     * Creates a new posture setting configuration.
     * @param command The command containing posture setting data
     * @return The ID of the created posture setting
     */
    Long handle(CreatePostureSettingCommand command);
    
    /**
     * Updates an existing posture setting configuration.
     * @param command The command containing updated posture setting data
     * @return The ID of the updated posture setting
     */
    Long handle(UpdatePostureSettingCommand command);
    
    /**
     * Deletes a posture setting configuration.
     * @param command The command containing the ID of the posture setting to delete
     */
    void handle(DeletePostureSettingCommand command);
}
