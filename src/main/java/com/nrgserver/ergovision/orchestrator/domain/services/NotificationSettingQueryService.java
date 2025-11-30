package com.nrgserver.ergovision.orchestrator.domain.services;

import com.nrgserver.ergovision.orchestrator.domain.model.aggregates.NotificationSetting;
import com.nrgserver.ergovision.orchestrator.domain.model.queries.GetNotificationSettingByIdQuery;
import com.nrgserver.ergovision.orchestrator.domain.model.queries.GetUserNotificationSettingQuery;

import java.util.Optional;

public interface NotificationSettingQueryService {
    Optional<NotificationSetting> handle(GetUserNotificationSettingQuery query);

    Optional<NotificationSetting> handle(GetNotificationSettingByIdQuery query);
}
