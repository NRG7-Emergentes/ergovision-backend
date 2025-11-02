package com.nrgserver.ergovision.orchestrator.domain.model.aggregates;

import com.nrgserver.ergovision.orchestrator.domain.model.valueobjects.PostureThresholds;
import com.nrgserver.ergovision.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * Aggregate: PostureSetting
 * Configures posture detection parameters.
 */
@Entity
@Getter
public class PostureSetting extends AuditableAbstractAggregateRoot<PostureSetting> {
    
    @Column(nullable = false)
    private Long userId;
    
    @Column(nullable = false)
    private Integer postureSensitivity;
    
    @Column(nullable = false)
    private Integer shoulderAngleThreshold;
    
    @Column(nullable = false)
    private Integer headAngleThreshold;
    
    @Column(nullable = false)
    private Integer samplingFrequency;
    
    @Column(nullable = false)
    private Boolean showSkeleton;
    
    @Embedded
    private PostureThresholds postureThresholds;
    
    protected PostureSetting() {
        // Required by JPA
    }
    
    public PostureSetting(Long userId) {
        this.userId = userId;
        this.postureSensitivity = 50;
        this.shoulderAngleThreshold = 15;
        this.headAngleThreshold = 10;
        this.samplingFrequency = 1;
        this.showSkeleton = true;
        this.postureThresholds = new PostureThresholds(15, 10, 20, 8);
    }
    
    public PostureSetting(Long userId, Integer postureSensitivity, Integer shoulderAngleThreshold,
                         Integer headAngleThreshold, Integer samplingFrequency, Boolean showSkeleton,
                         PostureThresholds postureThresholds) {
        this.userId = userId;
        this.postureSensitivity = validateSensitivity(postureSensitivity);
        this.shoulderAngleThreshold = validateAngleThreshold(shoulderAngleThreshold, "Shoulder");
        this.headAngleThreshold = validateAngleThreshold(headAngleThreshold, "Head");
        this.samplingFrequency = validateSamplingFrequency(samplingFrequency);
        this.showSkeleton = showSkeleton;
        this.postureThresholds = postureThresholds;
        
        if (postureThresholds != null && !postureThresholds.isValid()) {
            throw new IllegalArgumentException("Invalid posture thresholds");
        }
    }
    
    /**
     * Adjusts the detection sensitivity.
     * @param level The sensitivity level (0-100)
     */
    public void adjustSensitivity(Integer level) {
        this.postureSensitivity = validateSensitivity(level);
    }
    
    /**
     * Sets shoulder angle threshold.
     * @param angle The threshold angle in degrees
     */
    public void setShoulderThreshold(Integer angle) {
        this.shoulderAngleThreshold = validateAngleThreshold(angle, "Shoulder");
    }
    
    /**
     * Sets head angle threshold.
     * @param angle The threshold angle in degrees
     */
    public void setHeadThreshold(Integer angle) {
        this.headAngleThreshold = validateAngleThreshold(angle, "Head");
    }
    
    /**
     * Sets sampling frequency.
     * @param freq The sampling frequency in seconds
     */
    public void setSamplingFrequency(Integer freq) {
        this.samplingFrequency = validateSamplingFrequency(freq);
    }
    
    /**
     * Toggles skeleton visibility.
     */
    public void toggleSkeletonVisibility() {
        this.showSkeleton = !this.showSkeleton;
    }
    
    /**
     * Updates skeleton visibility.
     * @param visible Whether skeleton should be visible
     */
    public void setSkeletonVisibility(Boolean visible) {
        this.showSkeleton = visible;
    }
    
    /**
     * Updates all posture settings.
     */
    public void updateSettings(Integer postureSensitivity, Integer shoulderAngleThreshold,
                              Integer headAngleThreshold, Integer samplingFrequency, 
                              Boolean showSkeleton, PostureThresholds postureThresholds) {
        this.postureSensitivity = validateSensitivity(postureSensitivity);
        this.shoulderAngleThreshold = validateAngleThreshold(shoulderAngleThreshold, "Shoulder");
        this.headAngleThreshold = validateAngleThreshold(headAngleThreshold, "Head");
        this.samplingFrequency = validateSamplingFrequency(samplingFrequency);
        this.showSkeleton = showSkeleton;
        
        if (postureThresholds != null) {
            if (!postureThresholds.isValid()) {
                throw new IllegalArgumentException("Invalid posture thresholds");
            }
            this.postureThresholds = postureThresholds;
        }
    }
    
    private Integer validateSensitivity(Integer sensitivity) {
        if (sensitivity == null || sensitivity < 0 || sensitivity > 100) {
            throw new IllegalArgumentException("Posture sensitivity must be between 0 and 100");
        }
        return sensitivity;
    }
    
    private Integer validateAngleThreshold(Integer angle, String type) {
        if (angle == null || angle < 0 || angle > 90) {
            throw new IllegalArgumentException(type + " angle threshold must be between 0 and 90 degrees");
        }
        return angle;
    }
    
    private Integer validateSamplingFrequency(Integer frequency) {
        if (frequency == null || frequency < 1) {
            throw new IllegalArgumentException("Sampling frequency must be at least 1 second");
        }
        return frequency;
    }
}
