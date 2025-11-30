package com.nrgserver.ergovision.monitoring.domain.model.commands;

import java.time.Instant;
import java.util.Date;

public record CreateMonitoringSessionCommand(
        Long userId,
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
