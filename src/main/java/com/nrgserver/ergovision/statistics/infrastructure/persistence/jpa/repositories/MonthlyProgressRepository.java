package com.nrgserver.ergovision.statistics.infrastructure.persistence.jpa.repositories;

import com.nrgserver.ergovision.statistics.domain.model.entities.MonthlyProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyProgressRepository extends  JpaRepository<MonthlyProgress, Long> {
}
