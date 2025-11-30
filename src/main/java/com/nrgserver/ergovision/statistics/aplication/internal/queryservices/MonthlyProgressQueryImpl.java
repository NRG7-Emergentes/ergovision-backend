package com.nrgserver.ergovision.statistics.aplication.internal.queryservices;

import com.nrgserver.ergovision.statistics.domain.model.entities.MonthlyProgress;
import com.nrgserver.ergovision.statistics.domain.model.queries.GetMonthlyProgressByIdQuery;
import com.nrgserver.ergovision.statistics.domain.services.MonthlyProgressQueryService;
import com.nrgserver.ergovision.statistics.infrastructure.persistence.jpa.repositories.MonthlyProgressRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MonthlyProgressQueryImpl implements MonthlyProgressQueryService {
    private final MonthlyProgressRepository monthlyProgressRepository;

    public MonthlyProgressQueryImpl(MonthlyProgressRepository monthlyProgressRepository) {
        this.monthlyProgressRepository = monthlyProgressRepository;
    }


    @Override
    public Optional<MonthlyProgress> handle(GetMonthlyProgressByIdQuery query) {
        return this.monthlyProgressRepository.findById(query.monthlyProgressId());
    }
}
