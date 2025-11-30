package com.nrgserver.ergovision.statistics.domain.model.commands;

public record CreateOnlyMonthlyProgressCommand(
        String month,
        Double averageScore
) {
}
