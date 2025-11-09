package com.nrgserver.ergovision.monitoring.interfaces.rest.transform;

import com.nrgserver.ergovision.monitoring.domain.model.aggregates.MonitoringSession;
import com.nrgserver.ergovision.monitoring.interfaces.rest.resources.MonitoringSessionResource;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class MonitoringSessionResourceFromEntityAssembler {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
            .withZone(ZoneId.systemDefault());

    public static MonitoringSessionResource toResourceFromEntity(MonitoringSession entity) {
        return new MonitoringSessionResource(
                entity.getId(),
                entity.getStartDate() != null ? FORMATTER.format(entity.getStartDate()) : null,
                entity.getEndDate() != null ? FORMATTER.format(entity.getEndDate()) : null,
                entity.getScore(),
                entity.getGoodScore(),
                entity.getBadScore(),
                entity.getGoodPostureTime(),
                entity.getBadPostureTime(),
                entity.getDuration(),
                entity.getNumberOfPauses(),
                entity.getAveragePauseDuration()
        );
    }
}
