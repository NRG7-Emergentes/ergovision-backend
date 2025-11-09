package com.nrgserver.ergovision.monitoring.domain.model.commands;

import java.time.Instant;

public record CreateMonitoringSessionCommand(
        Long userId,
        Instant startAt,
        Instant endAt
) {
}
