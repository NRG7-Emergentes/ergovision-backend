package com.nrgserver.ergovision.monitoring.interfaces.rest.resources;

import java.time.Instant;

public record MonitoringSessionResource(
        Long id,
        Long userId,
        String startDate,
        String endDate,
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
