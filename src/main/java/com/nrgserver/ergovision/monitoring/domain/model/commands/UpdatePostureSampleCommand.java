package com.nrgserver.ergovision.monitoring.domain.model.commands;

import com.nrgserver.ergovision.monitoring.domain.model.valueobjects.PostureStatus;

import java.time.Instant;

public record UpdatePostureSampleCommand(
        Long postureSampleId,
        Long monitoringSessionId,
        Instant recordedAt,
        PostureStatus postureStatus
) {
}
