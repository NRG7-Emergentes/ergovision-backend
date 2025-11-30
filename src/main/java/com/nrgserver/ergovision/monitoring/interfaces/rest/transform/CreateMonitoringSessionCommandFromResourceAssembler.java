package com.nrgserver.ergovision.monitoring.interfaces.rest.transform;

import com.nrgserver.ergovision.monitoring.domain.model.commands.CreateMonitoringSessionCommand;
import com.nrgserver.ergovision.monitoring.interfaces.rest.resources.CreateMonitoringSessionResource;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class CreateMonitoringSessionCommandFromResourceAssembler {

    public static CreateMonitoringSessionCommand toCommandFromResource(CreateMonitoringSessionResource resource, Long userId) {
        return new CreateMonitoringSessionCommand(
                userId,
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
