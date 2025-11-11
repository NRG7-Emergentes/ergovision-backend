package com.nrgserver.ergovision.monitoring.aplication.internal.queryservices;

import com.nrgserver.ergovision.monitoring.domain.model.aggregates.MonitoringSession;
import com.nrgserver.ergovision.monitoring.domain.model.queries.GetMonitoringSessionByIdQuery;
import com.nrgserver.ergovision.monitoring.domain.services.MonitoringSessionQueryService;
import com.nrgserver.ergovision.monitoring.infrastructure.persistence.jpa.repositories.MonitoringSessionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MonitoringSessionQueryImpl implements MonitoringSessionQueryService {

    private final MonitoringSessionRepository monitoringSessionRepository;

    public MonitoringSessionQueryImpl(MonitoringSessionRepository monitoringSessionRepository) {
        this.monitoringSessionRepository = monitoringSessionRepository;
    }

    @Override
    public Optional<MonitoringSession> handle(GetMonitoringSessionByIdQuery query) {
        return this.monitoringSessionRepository.findById(query.monitoringSessionId());
    }
}
