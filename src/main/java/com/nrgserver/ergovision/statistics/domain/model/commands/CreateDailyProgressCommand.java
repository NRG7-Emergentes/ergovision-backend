package com.nrgserver.ergovision.statistics.domain.model.commands;

public record CreateDailyProgressCommand(
        String date,
        Double averageScore,
        Long statisticsId
) {
}
