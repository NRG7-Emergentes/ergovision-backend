package com.nrgserver.ergovision.statistics.infrastructure.persistence.jpa.repositories;

import com.nrgserver.ergovision.statistics.domain.model.aggregates.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    Boolean existsByUserId (Long userId);
}
