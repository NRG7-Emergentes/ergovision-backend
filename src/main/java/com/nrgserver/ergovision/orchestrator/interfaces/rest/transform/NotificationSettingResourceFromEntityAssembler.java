package com.nrgserver.ergovision.orchestrator.interfaces.rest.transform;

import com.nrgserver.ergovision.orchestrator.domain.model.aggregates.NotificationSetting;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.resources.NotificationSettingResource;

public class NotificationSettingResourceFromEntityAssembler {
    public static NotificationSettingResource toResourceFromEntity(NotificationSetting entity) {
        return new NotificationSettingResource(
                entity.getId(),
                entity.getUserId(),
                entity.getEmailNotifications()
        );
    }
}
