package com.nrgserver.ergovision.monitoring.domain.model.commands;

import java.time.Instant;

public record UpdateMonitoringSessionCommand(
        Long monitoringSessionId,
        Long userId,
        Instant startAt,
        Instant endAt
) {
}
