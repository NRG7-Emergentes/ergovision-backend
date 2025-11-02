package com.nrgserver.ergovision.orchestrator.interfaces.rest.transform;

import com.nrgserver.ergovision.orchestrator.domain.model.valueobjects.PostureThresholds;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.resources.PostureThresholdsResource;

public class PostureThresholdsResourceFromValueObjectAssembler {
    
    public static PostureThresholdsResource toResourceFromValueObject(PostureThresholds valueObject) {
        if (valueObject == null) {
            return null;
        }
        
        return new PostureThresholdsResource(
                valueObject.getShoulderAngle(),
                valueObject.getNeckAngle(),
                valueObject.getBackAngle(),
                valueObject.getHeadTilt()
        );
    }
    
    public static PostureThresholds toValueObjectFromResource(PostureThresholdsResource resource) {
        if (resource == null) {
            return null;
        }
        
        return new PostureThresholds(
                resource.shoulderAngle(),
                resource.neckAngle(),
                resource.backAngle(),
                resource.headTilt()
        );
    }
}
