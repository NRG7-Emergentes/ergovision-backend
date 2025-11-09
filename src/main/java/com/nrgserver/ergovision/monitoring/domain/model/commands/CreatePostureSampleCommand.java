package com.nrgserver.ergovision.monitoring.domain.model.commands;

import com.nrgserver.ergovision.monitoring.domain.model.valueobjects.PostureStatus;

import java.time.Instant;

public record CreatePostureSampleCommand(
        Long monitoringSessionId,
        Instant recordedAt,
        PostureStatus postureStatus
) {
}
