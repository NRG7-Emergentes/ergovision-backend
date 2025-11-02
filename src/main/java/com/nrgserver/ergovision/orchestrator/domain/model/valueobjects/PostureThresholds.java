package com.nrgserver.ergovision.orchestrator.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Value Object: PostureThresholds
 * Defines angular thresholds for posture detection.
 */
@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostureThresholds {
    
    private Integer shoulderAngle;
    private Integer neckAngle;
    private Integer backAngle;
    private Integer headTilt;
    
    /**
     * Validates that all threshold values are within acceptable ranges (0-180 degrees)
     * @return true if all values are valid, false otherwise
     */
    public boolean isValid() {
        return isAngleValid(shoulderAngle) && 
               isAngleValid(neckAngle) && 
               isAngleValid(backAngle) && 
               isAngleValid(headTilt);
    }
    
    private boolean isAngleValid(Integer angle) {
        return angle != null && angle >= 0 && angle <= 180;
    }
}
