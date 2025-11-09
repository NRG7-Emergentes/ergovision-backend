package com.nrgserver.ergovision.monitoring.domain.services;

import com.nrgserver.ergovision.monitoring.domain.model.entities.PostureSample;
import com.nrgserver.ergovision.monitoring.domain.model.queries.GetPostureSampleByIdQuery;

import java.util.Optional;

public interface PostureSampleQueryService {
    Optional<PostureSample> handle(GetPostureSampleByIdQuery query);

}
