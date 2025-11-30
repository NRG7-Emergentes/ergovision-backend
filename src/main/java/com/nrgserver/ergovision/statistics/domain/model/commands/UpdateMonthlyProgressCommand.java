package com.nrgserver.ergovision.statistics.domain.model.commands;

public record UpdateMonthlyProgressCommand(
        Long monthlyProgressId,
        String month,
        Double averageScore
) {
}
