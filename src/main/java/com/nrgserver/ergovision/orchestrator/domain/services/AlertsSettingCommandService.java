package com.nrgserver.ergovision.orchestrator.domain.services;

import com.nrgserver.ergovision.orchestrator.domain.model.commands.CreateAlertsSettingCommand;
import com.nrgserver.ergovision.orchestrator.domain.model.commands.DeleteAlertsSettingCommand;
import com.nrgserver.ergovision.orchestrator.domain.model.commands.UpdateAlertsSettingCommand;

/**
 * Service: AlertsSettingCommandService
 * Manages write operations for alert settings.
 */
public interface AlertsSettingCommandService {
    
    /**
     * Creates a new alert setting configuration.
     * @param command The command containing alert setting data
     * @return The ID of the created alert setting
     */
    Long handle(CreateAlertsSettingCommand command);
    
    /**
     * Updates an existing alert setting configuration.
     * @param command The command containing updated alert setting data
     * @return The ID of the updated alert setting
     */
    Long handle(UpdateAlertsSettingCommand command);
    
    /**
     * Deletes an alert setting configuration.
     * @param command The command containing the ID of the alert setting to delete
     */
    void handle(DeleteAlertsSettingCommand command);
}
