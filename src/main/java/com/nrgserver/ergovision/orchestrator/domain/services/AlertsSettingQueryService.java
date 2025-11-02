package com.nrgserver.ergovision.orchestrator.domain.services;

import com.nrgserver.ergovision.orchestrator.domain.model.aggregates.AlertSetting;
import com.nrgserver.ergovision.orchestrator.domain.model.queries.GetAlertSettingByIdQuery;
import com.nrgserver.ergovision.orchestrator.domain.model.queries.GetUserAlertSettingQuery;

import java.util.Optional;

/**
 * Service: AlertsSettingQueryService
 * Manages read operations for alert settings.
 */
public interface AlertsSettingQueryService {
    
    /**
     * Retrieves alert setting for a specific user.
     * @param query The query containing the user ID
     * @return Optional containing the alert setting if found
     */
    Optional<AlertSetting> handle(GetUserAlertSettingQuery query);
    
    /**
     * Retrieves alert setting by its ID.
     * @param query The query containing the setting ID
     * @return Optional containing the alert setting if found
     */
    Optional<AlertSetting> handle(GetAlertSettingByIdQuery query);
}
