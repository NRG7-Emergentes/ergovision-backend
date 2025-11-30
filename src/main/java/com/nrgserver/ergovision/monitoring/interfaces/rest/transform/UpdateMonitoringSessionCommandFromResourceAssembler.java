package com.nrgserver.ergovision.monitoring.interfaces.rest.transform;

import com.nrgserver.ergovision.monitoring.domain.model.commands.UpdateMonitoringSessionCommand;
import com.nrgserver.ergovision.monitoring.interfaces.rest.resources.MonitoringSessionResource;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class UpdateMonitoringSessionCommandFromResourceAssembler {

    public static UpdateMonitoringSessionCommand toCommandFromResource(Long monitoringSessionId, MonitoringSessionResource resource) {
        return new UpdateMonitoringSessionCommand(
                monitoringSessionId,
                resource.startDate(),
                resource.endDate(),
                resource.score(),
                resource.goodScore(),
                resource.badScore(),
                resource.goodPostureTime(),
                resource.badPostureTime(),
                resource.duration(),
                resource.numberOfPauses(),
                resource.averagePauseDuration()
        );
    }

}
