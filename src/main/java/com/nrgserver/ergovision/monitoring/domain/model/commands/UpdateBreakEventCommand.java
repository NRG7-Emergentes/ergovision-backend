package com.nrgserver.ergovision.monitoring.domain.model.commands;

import java.time.Instant;

public record UpdateBreakEventCommand(
        Long breakEventId,
        Long monitoringSessionId,
        Instant startedAt,
        Instant endedAt
) {
}
