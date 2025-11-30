package com.nrgserver.ergovision.monitoring.domain.model.commands;

import java.time.Instant;
import java.util.Date;

public record UpdateMonitoringSessionCommand(
        Long monitoringSessionId,
        Date startDate,
        Date endDate,
        Double score,
        Double goodScore,
        Double badScore,
        Double goodPostureTime,
        Double badPostureTime,
        Double duration,
        Integer numberOfPauses,
        Double averagePauseDuration
) {
}
