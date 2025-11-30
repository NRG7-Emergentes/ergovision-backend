package com.nrgserver.ergovision.statistics.domain.model.commands;

public record UpdateDailyProgressCommand(
        Long dailyProgressId,
        String date,
        Double averageScore
) {
}
