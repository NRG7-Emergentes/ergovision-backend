package com.nrgserver.ergovision.monitoring.interfaces.rest.transform;

import com.nrgserver.ergovision.monitoring.domain.model.aggregates.MonitoringSession;
import com.nrgserver.ergovision.monitoring.interfaces.rest.resources.MonitoringSessionResource;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class MonitoringSessionResourceFromEntityAssembler {

    public static MonitoringSessionResource toResourceFromEntity(MonitoringSession entity) {
        return new MonitoringSessionResource(
                entity.getId(),
                entity.getUserId(),
                entity.getStartDate() ,
                entity.getEndDate() ,
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
