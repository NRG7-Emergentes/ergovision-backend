package com.nrgserver.ergovision.monitoring.interfaces.acl;

import com.nrgserver.ergovision.monitoring.domain.model.aggregates.MonitoringSession;
import com.nrgserver.ergovision.monitoring.domain.model.queries.GetAllMonitoringSessionsByUserIdQuery;
import com.nrgserver.ergovision.monitoring.domain.model.queries.GetMonitoringSessionByUserIdQuery;
import com.nrgserver.ergovision.monitoring.domain.services.MonitoringSessionQueryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitoringSessionContextFacade {
    private final MonitoringSessionQueryService monitoringSessionQueryService;

    public MonitoringSessionContextFacade(MonitoringSessionQueryService monitoringSessionQueryService) {
        this.monitoringSessionQueryService = monitoringSessionQueryService;
    }

    public List<MonitoringSession> getMonitoringSessionsByUserId(Long userId) {
        var query = new GetAllMonitoringSessionsByUserIdQuery(userId);
        return monitoringSessionQueryService.handle(query);
    }
}
