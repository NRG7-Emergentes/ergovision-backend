package com.nrgserver.ergovision.monitoring.domain.services;

import com.nrgserver.ergovision.monitoring.domain.model.aggregates.MonitoringSession;
import com.nrgserver.ergovision.monitoring.domain.model.queries.GetAllMonitoringSessionsByUserIdQuery;
import com.nrgserver.ergovision.monitoring.domain.model.queries.GetMonitoringSessionByIdQuery;

import java.util.List;
import java.util.Optional;

public interface MonitoringSessionQueryService {
    Optional<MonitoringSession> handle(GetMonitoringSessionByIdQuery query);
    List<MonitoringSession> handle(GetAllMonitoringSessionsByUserIdQuery query);
}
