package com.nrgserver.ergovision.statistics.domain.services;

import com.nrgserver.ergovision.statistics.domain.model.entities.MonthlyProgress;
import com.nrgserver.ergovision.statistics.domain.model.queries.GetMonthlyProgressByIdQuery;

import java.util.Optional;

public interface MonthlyProgressQueryService {
    Optional<MonthlyProgress> handle(GetMonthlyProgressByIdQuery query);
}
