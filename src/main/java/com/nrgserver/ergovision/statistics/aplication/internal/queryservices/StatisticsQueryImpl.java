package com.nrgserver.ergovision.statistics.aplication.internal.queryservices;

import com.nrgserver.ergovision.statistics.domain.model.aggregates.Statistics;
import com.nrgserver.ergovision.statistics.domain.model.queries.GetStatisticsByIdQuery;
import com.nrgserver.ergovision.statistics.domain.services.StatisticsQueryService;
import com.nrgserver.ergovision.statistics.infrastructure.persistence.jpa.repositories.StatisticsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StatisticsQueryImpl implements StatisticsQueryService {

    private final StatisticsRepository statisticsRepository;

    public StatisticsQueryImpl(StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    @Override
    public Optional<Statistics> handle(GetStatisticsByIdQuery query) {
        return this.statisticsRepository.findById(query.statisticsId());
    }
}
