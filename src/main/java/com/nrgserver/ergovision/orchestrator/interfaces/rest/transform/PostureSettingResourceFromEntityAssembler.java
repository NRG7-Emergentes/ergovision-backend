package com.nrgserver.ergovision.orchestrator.interfaces.rest.transform;

import com.nrgserver.ergovision.orchestrator.domain.model.aggregates.PostureSetting;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.resources.PostureSettingResource;

public class PostureSettingResourceFromEntityAssembler {
    
    public static PostureSettingResource toResourceFromEntity(PostureSetting entity) {
        return new PostureSettingResource(
                entity.getId(),
                entity.getUserId(),
                entity.getPostureSensitivity(),
                entity.getSamplingFrequency(),
                entity.getShowSkeleton()
        );
    }
}
