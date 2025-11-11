package com.nrgserver.ergovision.orchestrator.application.internal.commandservices;

import com.nrgserver.ergovision.orchestrator.domain.model.aggregates.AlertSetting;
import com.nrgserver.ergovision.orchestrator.domain.model.commands.CreateAlertsSettingCommand;
import com.nrgserver.ergovision.orchestrator.domain.model.commands.DeleteAlertsSettingCommand;
import com.nrgserver.ergovision.orchestrator.domain.model.commands.UpdateAlertsSettingCommand;
import com.nrgserver.ergovision.orchestrator.domain.services.AlertsSettingCommandService;
import com.nrgserver.ergovision.orchestrator.infrastructure.persistence.jpa.repositories.AlertSettingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlertsSettingCommandServiceImpl implements AlertsSettingCommandService {
    
    private final AlertSettingRepository alertSettingRepository;
    
    public AlertsSettingCommandServiceImpl(AlertSettingRepository alertSettingRepository) {
        this.alertSettingRepository = alertSettingRepository;
    }
    
    @Override
    @Transactional
    public Long handle(CreateAlertsSettingCommand command) {
        if (alertSettingRepository.existsByUserId(command.userId())) {
            throw new IllegalArgumentException("Alert settings already exist for user: " + command.userId());
        }
        
        AlertSetting alertSetting = new AlertSetting(
                command.userId(),
                command.visualAlertsEnabled(),
                command.soundAlertsEnabled(),
                command.alertVolume(),
                command.alertInterval()
        );
        
        AlertSetting savedSetting = alertSettingRepository.save(alertSetting);
        return savedSetting.getId();
    }
    
    @Override
    @Transactional
    public Long handle(UpdateAlertsSettingCommand command) {
        AlertSetting alertSetting = alertSettingRepository.findById(command.settingId())
                .orElseThrow(() -> new IllegalArgumentException("Alert setting not found with id: " + command.settingId()));
        
        alertSetting.updateSettings(
                command.visualAlertsEnabled(),
                command.soundAlertsEnabled(),
                command.alertVolume(),
                command.alertInterval()
        );
        
        AlertSetting updatedSetting = alertSettingRepository.save(alertSetting);
        return updatedSetting.getId();
    }
    
    @Override
    @Transactional
    public void handle(DeleteAlertsSettingCommand command) {
        if (!alertSettingRepository.existsById(command.settingId())) {
            throw new IllegalArgumentException("Alert setting not found with id: " + command.settingId());
        }
        
        alertSettingRepository.deleteById(command.settingId());
    }
}
