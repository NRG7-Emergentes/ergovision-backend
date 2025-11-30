package com.nrgserver.ergovision.statistics.aplication.internal.queryservices;

import com.nrgserver.ergovision.statistics.domain.model.entities.DailyProgress;
import com.nrgserver.ergovision.statistics.domain.model.queries.GetDailyProgressByIdQuery;
import com.nrgserver.ergovision.statistics.domain.services.DailyProgressQueryService;
import com.nrgserver.ergovision.statistics.infrastructure.persistence.jpa.repositories.DailyProgressRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DailyProgressQueryImpl implements DailyProgressQueryService {

    private final DailyProgressRepository dailyProgressRepository;

    public DailyProgressQueryImpl(DailyProgressRepository dailyProgressRepository) {
        this.dailyProgressRepository = dailyProgressRepository;
    }

    @Override
    public Optional<DailyProgress> handle(GetDailyProgressByIdQuery query) {
        return this.dailyProgressRepository.findById(query.dailyProgressId());
    }
}
