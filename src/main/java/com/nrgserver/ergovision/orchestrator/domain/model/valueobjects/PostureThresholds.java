package com.nrgserver.ergovision.orchestrator.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostureThresholds {
    
    private Integer shoulderAngle;
    private Integer neckAngle;
    private Integer backAngle;
    private Integer headTilt;
    
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
