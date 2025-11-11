package com.nrgserver.ergovision.monitoring.domain.services;

import com.nrgserver.ergovision.monitoring.domain.model.aggregates.MonitoringSession;
import com.nrgserver.ergovision.monitoring.domain.model.commands.CreateMonitoringSessionCommand;
import com.nrgserver.ergovision.monitoring.domain.model.commands.DeleteMonitoringSessionCommand;
import com.nrgserver.ergovision.monitoring.domain.model.commands.UpdateMonitoringSessionCommand;

import java.util.Optional;

public interface MonitoringSessionCommandService {
    Long handle(CreateMonitoringSessionCommand command);
    Optional<MonitoringSession> handle(UpdateMonitoringSessionCommand command);
    void handle(DeleteMonitoringSessionCommand command);

}
