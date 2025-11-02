package com.nrgserver.ergovision.orchestrator.domain.services;

import com.nrgserver.ergovision.orchestrator.domain.model.aggregates.PostureSetting;
import com.nrgserver.ergovision.orchestrator.domain.model.queries.GetPostureSettingByIdQuery;
import com.nrgserver.ergovision.orchestrator.domain.model.queries.GetUserPostureSettingQuery;

import java.util.Optional;

/**
 * Service: PostureSettingQueryService
 * Manages read operations for posture settings.
 */
public interface PostureSettingQueryService {
    
    /**
     * Retrieves posture setting for a specific user.
     * @param query The query containing the user ID
     * @return Optional containing the posture setting if found
     */
    Optional<PostureSetting> handle(GetUserPostureSettingQuery query);
    
    /**
     * Retrieves posture setting by its ID.
     * @param query The query containing the setting ID
     * @return Optional containing the posture setting if found
     */
    Optional<PostureSetting> handle(GetPostureSettingByIdQuery query);
}
