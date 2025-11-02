package com.nrgserver.ergovision.orchestrator.domain.services;

import com.nrgserver.ergovision.orchestrator.domain.model.aggregates.AlertSetting;
import com.nrgserver.ergovision.orchestrator.domain.model.queries.GetAlertSettingByIdQuery;
import com.nrgserver.ergovision.orchestrator.domain.model.queries.GetUserAlertSettingQuery;

import java.util.Optional;

public interface AlertsSettingQueryService {
    
    Optional<AlertSetting> handle(GetUserAlertSettingQuery query);
    
    Optional<AlertSetting> handle(GetAlertSettingByIdQuery query);
}
