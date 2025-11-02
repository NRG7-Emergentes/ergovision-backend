package com.nrgserver.ergovision.orchestrator.application.internal.queryservices;

import com.nrgserver.ergovision.orchestrator.domain.model.aggregates.PostureSetting;
import com.nrgserver.ergovision.orchestrator.domain.model.queries.GetPostureSettingByIdQuery;
import com.nrgserver.ergovision.orchestrator.domain.model.queries.GetUserPostureSettingQuery;
import com.nrgserver.ergovision.orchestrator.domain.services.PostureSettingQueryService;
import com.nrgserver.ergovision.orchestrator.infrastructure.persistence.jpa.repositories.PostureSettingRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of PostureSettingQueryService.
 * Handles query operations for posture settings.
 */
@Service
public class PostureSettingQueryServiceImpl implements PostureSettingQueryService {
    
    private final PostureSettingRepository postureSettingRepository;
    
    public PostureSettingQueryServiceImpl(PostureSettingRepository postureSettingRepository) {
        this.postureSettingRepository = postureSettingRepository;
    }
    
    @Override
    public Optional<PostureSetting> handle(GetUserPostureSettingQuery query) {
        return postureSettingRepository.findByUserId(query.userId());
    }
    
    @Override
    public Optional<PostureSetting> handle(GetPostureSettingByIdQuery query) {
        return postureSettingRepository.findById(query.settingId());
    }
}
