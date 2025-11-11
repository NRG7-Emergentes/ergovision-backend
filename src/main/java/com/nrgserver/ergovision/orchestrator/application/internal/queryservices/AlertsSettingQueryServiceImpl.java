package com.nrgserver.ergovision.orchestrator.application.internal.queryservices;

import com.nrgserver.ergovision.orchestrator.domain.model.aggregates.AlertSetting;
import com.nrgserver.ergovision.orchestrator.domain.model.queries.GetAlertSettingByIdQuery;
import com.nrgserver.ergovision.orchestrator.domain.model.queries.GetUserAlertSettingQuery;
import com.nrgserver.ergovision.orchestrator.domain.services.AlertsSettingQueryService;
import com.nrgserver.ergovision.orchestrator.infrastructure.persistence.jpa.repositories.AlertSettingRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AlertsSettingQueryServiceImpl implements AlertsSettingQueryService {
    
    private final AlertSettingRepository alertSettingRepository;
    
    public AlertsSettingQueryServiceImpl(AlertSettingRepository alertSettingRepository) {
        this.alertSettingRepository = alertSettingRepository;
    }
    
    @Override
    public Optional<AlertSetting> handle(GetUserAlertSettingQuery query) {
        return alertSettingRepository.findByUserId(query.userId());
    }
    
    @Override
    public Optional<AlertSetting> handle(GetAlertSettingByIdQuery query) {
        return alertSettingRepository.findById(query.settingId());
    }
}
