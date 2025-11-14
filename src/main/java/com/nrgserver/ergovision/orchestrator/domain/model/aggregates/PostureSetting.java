package com.nrgserver.ergovision.orchestrator.domain.model.aggregates;

import com.nrgserver.ergovision.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class PostureSetting extends AuditableAbstractAggregateRoot<PostureSetting> {
    
    @Column(nullable = false)
    private Long userId;
    
    @Column(nullable = false)
    private Integer postureSensitivity;
    
    @Column(nullable = false)
    private Integer samplingFrequency;
    
    @Column(nullable = false)
    private Boolean showSkeleton;
    
    protected PostureSetting() {
    }
    
    public PostureSetting(Long userId) {
        this.userId = userId;
        this.postureSensitivity = 80;
        this.samplingFrequency = 1;
        this.showSkeleton = true;
    }
    
    public PostureSetting(Long userId, Integer postureSensitivity, Integer samplingFrequency, Boolean showSkeleton) {
        this.userId = userId;
        this.postureSensitivity = validateSensitivity(postureSensitivity);
        this.samplingFrequency = validateSamplingFrequency(samplingFrequency);
        this.showSkeleton = showSkeleton;
    }
    
    public void adjustSensitivity(Integer level) {
        this.postureSensitivity = validateSensitivity(level);
    }

    public void setSamplingFrequency(Integer freq) {
        this.samplingFrequency = validateSamplingFrequency(freq);
    }
    
    public void toggleSkeletonVisibility() {
        this.showSkeleton = !this.showSkeleton;
    }
    
    public void setSkeletonVisibility(Boolean visible) {
        this.showSkeleton = visible;
    }
    
    public void updateSettings(Integer postureSensitivity, Integer samplingFrequency,
                              Boolean showSkeleton) {
        this.postureSensitivity = validateSensitivity(postureSensitivity);
        this.samplingFrequency = validateSamplingFrequency(samplingFrequency);
        this.showSkeleton = showSkeleton;
    }
    
    private Integer validateSensitivity(Integer sensitivity) {
        if (sensitivity == null || sensitivity < 0 || sensitivity > 100) {
            throw new IllegalArgumentException("Posture sensitivity must be between 0 and 100");
        }
        return sensitivity;
    }
    
    private Integer validateSamplingFrequency(Integer frequency) {
        if (frequency == null || frequency < 1) {
            throw new IllegalArgumentException("Sampling frequency must be at least 1 second");
        }
        return frequency;
    }
}
