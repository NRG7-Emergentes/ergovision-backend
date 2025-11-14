package com.nrgserver.ergovision.orchestrator.domain.model.aggregates;

import com.nrgserver.ergovision.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class AlertSetting extends AuditableAbstractAggregateRoot<AlertSetting> {
    
    @Column(nullable = false)
    private Long userId;
    
    @Column(nullable = false)
    private Boolean visualAlertsEnabled;
    
    @Column(nullable = false)
    private Boolean soundAlertsEnabled;
    
    @Column(nullable = false)
    private Integer alertVolume;
    
    @Column(nullable = false)
    private Integer pauseInterval;
    
    protected AlertSetting() {
    }
    
    public AlertSetting(Long userId) {
        this.userId = userId;
        this.visualAlertsEnabled = true;
        this.soundAlertsEnabled = true;
        this.alertVolume = 50;
        this.pauseInterval = 30;
    }
    
    public AlertSetting(Long userId, Boolean visualAlertsEnabled, Boolean soundAlertsEnabled, 
                        Integer alertVolume, Integer alertInterval) {
        this.userId = userId;
        this.visualAlertsEnabled = visualAlertsEnabled;
        this.soundAlertsEnabled = soundAlertsEnabled;
        this.alertVolume = validateVolume(alertVolume);
        this.pauseInterval = validateInterval(alertInterval);
    }
    
    public void enableVisualAlerts() {
        this.visualAlertsEnabled = true;
    }
    
    public void disableVisualAlerts() {
        this.visualAlertsEnabled = false;
    }
    
    public void enableSoundAlerts() {
        this.soundAlertsEnabled = true;
    }
    
    public void disableSoundAlerts() {
        this.soundAlertsEnabled = false;
    }
    
    public void adjustVolume(Integer volume) {
        this.alertVolume = validateVolume(volume);
    }
    
    public void setPauseInterval(Integer interval) {
        this.pauseInterval = validateInterval(interval);
    }
    
    public void updateSettings(Boolean visualAlertsEnabled, Boolean soundAlertsEnabled, 
                              Integer alertVolume, Integer alertInterval) {
        this.visualAlertsEnabled = visualAlertsEnabled;
        this.soundAlertsEnabled = soundAlertsEnabled;
        this.alertVolume = validateVolume(alertVolume);
        this.pauseInterval = validateInterval(alertInterval);
    }
    
    private Integer validateVolume(Integer volume) {
        if (volume == null || volume < 0 || volume > 100) {
            throw new IllegalArgumentException("Alert volume must be between 0 and 100");
        }
        return volume;
    }
    
    private Integer validateInterval(Integer interval) {
        if (interval == null || interval < 1) {
            throw new IllegalArgumentException("Alert interval must be at least 1 second");
        }
        return interval;
    }
}
