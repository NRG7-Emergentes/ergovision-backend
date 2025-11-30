package com.nrgserver.ergovision.monitoring.domain.model.commands;

import java.time.Instant;

public record UpdateMonitoringSessionCommand(
        Long monitoringSessionId,
        Long userId,
        Instant startDate,
        Instant endDate,
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
