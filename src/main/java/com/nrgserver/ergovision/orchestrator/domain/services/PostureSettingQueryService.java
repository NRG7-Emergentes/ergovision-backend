package com.nrgserver.ergovision.orchestrator.domain.services;

import com.nrgserver.ergovision.orchestrator.domain.model.aggregates.PostureSetting;
import com.nrgserver.ergovision.orchestrator.domain.model.queries.GetPostureSettingByIdQuery;
import com.nrgserver.ergovision.orchestrator.domain.model.queries.GetUserPostureSettingQuery;

import java.util.Optional;

public interface PostureSettingQueryService {
    
    Optional<PostureSetting> handle(GetUserPostureSettingQuery query);
    
    Optional<PostureSetting> handle(GetPostureSettingByIdQuery query);
}
