package com.nrgserver.ergovision.monitoring.domain.services;

import com.nrgserver.ergovision.monitoring.domain.model.aggregates.MonitoringSession;
import com.nrgserver.ergovision.monitoring.domain.model.queries.GetMonitoringSessionByIdQuery;

import java.util.Optional;

public interface MonitoringSessionQueryService {
    Optional<MonitoringSession> handle(GetMonitoringSessionByIdQuery query);
}
