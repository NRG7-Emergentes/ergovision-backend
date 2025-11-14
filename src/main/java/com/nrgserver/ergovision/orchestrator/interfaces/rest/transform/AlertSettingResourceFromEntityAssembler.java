package com.nrgserver.ergovision.orchestrator.interfaces.rest.transform;

import com.nrgserver.ergovision.orchestrator.domain.model.aggregates.AlertSetting;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.resources.AlertSettingResource;

public class AlertSettingResourceFromEntityAssembler {
    
    public static AlertSettingResource toResourceFromEntity(AlertSetting entity) {
        return new AlertSettingResource(
                entity.getId(),
                entity.getUserId(),
                entity.getVisualAlertsEnabled(),
                entity.getSoundAlertsEnabled(),
                entity.getAlertVolume(),
                entity.getPauseInterval()
        );
    }
}
