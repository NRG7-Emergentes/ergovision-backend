package com.nrgserver.ergovision.monitoring.domain.model.commands;

import java.time.Instant;

public record CreateBreakEventCommand(
        Long monitoringSessionId,
        Instant startedAt,
        Instant endedAt
) {
}
