package com.nrgserver.ergovision.monitoring.domain.model.commands;

import com.nrgserver.ergovision.monitoring.domain.model.entities.PostureSample;

import java.time.Instant;
import java.util.List;

public record CreateMonitoringSessionCompletedCommand(
        Long userId,
        Instant startAt,
        Instant endAt,
        List<CreatePostureSampleCommand> samples,
        List<CreateBreakEventCommand> breakEvents
) {
}
