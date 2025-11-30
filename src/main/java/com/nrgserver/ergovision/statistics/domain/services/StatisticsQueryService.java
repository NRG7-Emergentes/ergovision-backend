package com.nrgserver.ergovision.statistics.domain.services;

import com.nrgserver.ergovision.statistics.domain.model.aggregates.Statistics;
import com.nrgserver.ergovision.statistics.domain.model.queries.GetStatisticsByIdQuery;

import java.util.Optional;

public interface StatisticsQueryService {
    Optional<Statistics> handle(GetStatisticsByIdQuery query);
}
