package com.nrgserver.ergovision.orchestrator.domain.model.aggregates;

import com.nrgserver.ergovision.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * Aggregate: AlertSetting
 * Manages alert configuration for monitoring.
 */
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
    private Integer alertInterval;
    
    protected AlertSetting() {
        // Required by JPA
    }
    
    public AlertSetting(Long userId) {
        this.userId = userId;
        this.visualAlertsEnabled = true;
        this.soundAlertsEnabled = true;
        this.alertVolume = 50;
        this.alertInterval = 30;
    }
    
    public AlertSetting(Long userId, Boolean visualAlertsEnabled, Boolean soundAlertsEnabled, 
                        Integer alertVolume, Integer alertInterval) {
        this.userId = userId;
        this.visualAlertsEnabled = visualAlertsEnabled;
        this.soundAlertsEnabled = soundAlertsEnabled;
        this.alertVolume = validateVolume(alertVolume);
        this.alertInterval = validateInterval(alertInterval);
    }
    
    /**
     * Enables visual alerts.
     */
    public void enableVisualAlerts() {
        this.visualAlertsEnabled = true;
    }
    
    /**
     * Disables visual alerts.
     */
    public void disableVisualAlerts() {
        this.visualAlertsEnabled = false;
    }
    
    /**
     * Enables sound alerts.
     */
    public void enableSoundAlerts() {
        this.soundAlertsEnabled = true;
    }
    
    /**
     * Disables sound alerts.
     */
    public void disableSoundAlerts() {
        this.soundAlertsEnabled = false;
    }
    
    /**
     * Adjusts the volume (0-100).
     * @param volume The new volume level
     */
    public void adjustVolume(Integer volume) {
        this.alertVolume = validateVolume(volume);
    }
    
    /**
     * Sets the alert interval.
     * @param interval The interval between alerts in seconds
     */
    public void setAlertInterval(Integer interval) {
        this.alertInterval = validateInterval(interval);
    }
    
    /**
     * Updates all alert settings.
     * @param visualAlertsEnabled Whether visual alerts are enabled
     * @param soundAlertsEnabled Whether sound alerts are enabled
     * @param alertVolume The alert volume (0-100)
     * @param alertInterval The interval between alerts in seconds
     */
    public void updateSettings(Boolean visualAlertsEnabled, Boolean soundAlertsEnabled, 
                              Integer alertVolume, Integer alertInterval) {
        this.visualAlertsEnabled = visualAlertsEnabled;
        this.soundAlertsEnabled = soundAlertsEnabled;
        this.alertVolume = validateVolume(alertVolume);
        this.alertInterval = validateInterval(alertInterval);
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
