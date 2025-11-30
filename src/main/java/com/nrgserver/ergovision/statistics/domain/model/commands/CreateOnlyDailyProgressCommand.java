package com.nrgserver.ergovision.statistics.domain.model.commands;

public record CreateOnlyDailyProgressCommand(
        String date,
        Double averageScore
) {
}
