package com.nrgserver.ergovision.monitoring.infrastructure.persistence.jpa.repositories;

import com.nrgserver.ergovision.monitoring.domain.model.aggregates.MonitoringSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonitoringSessionRepository extends JpaRepository<MonitoringSession, Long> {
    List<MonitoringSession> findByUserId(Long userId);
}
