package com.nrgserver.ergovision.statistics.domain.services;

import com.nrgserver.ergovision.statistics.domain.model.entities.DailyProgress;
import com.nrgserver.ergovision.statistics.domain.model.queries.GetDailyProgressByIdQuery;

import java.util.Optional;

public interface DailyProgressQueryService {
    Optional<DailyProgress> handle(GetDailyProgressByIdQuery query);
}
