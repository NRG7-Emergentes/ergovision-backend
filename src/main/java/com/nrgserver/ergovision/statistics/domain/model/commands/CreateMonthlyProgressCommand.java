package com.nrgserver.ergovision.statistics.domain.model.commands;

public record CreateMonthlyProgressCommand(
        String month,
        Double averageScore,
        Long statisticsId
) {
}
