package com.nrgserver.ergovision.monitoring.interfaces.rest.resources;

import java.time.Instant;
import java.util.Date;

public record CreateMonitoringSessionResource (
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
){
}
