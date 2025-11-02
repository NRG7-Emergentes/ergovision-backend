package com.nrgserver.ergovision.orchestrator.interfaces.rest.transform;

import com.nrgserver.ergovision.orchestrator.domain.model.aggregates.PostureSetting;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.resources.PostureSettingResource;

/**
 * Transformer: PostureSettingResourceFromEntityAssembler
 * Converts PostureSetting entity to PostureSettingResource.
 */
public class PostureSettingResourceFromEntityAssembler {
    
    public static PostureSettingResource toResourceFromEntity(PostureSetting entity) {
        return new PostureSettingResource(
                entity.getId(),
                entity.getUserId(),
                entity.getPostureSensitivity(),
                entity.getShoulderAngleThreshold(),
                entity.getHeadAngleThreshold(),
                entity.getSamplingFrequency(),
                entity.getShowSkeleton(),
                PostureThresholdsResourceFromValueObjectAssembler.toResourceFromValueObject(
                        entity.getPostureThresholds()
                )
        );
    }
}
