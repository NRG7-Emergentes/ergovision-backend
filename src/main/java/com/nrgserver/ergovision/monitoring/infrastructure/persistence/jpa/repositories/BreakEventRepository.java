package com.nrgserver.ergovision.monitoring.infrastructure.persistence.jpa.repositories;

import com.nrgserver.ergovision.monitoring.domain.model.entities.BreakEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BreakEventRepository extends JpaRepository<BreakEvent, Long> {
}
