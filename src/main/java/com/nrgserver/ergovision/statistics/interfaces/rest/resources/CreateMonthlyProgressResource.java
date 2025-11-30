package com.nrgserver.ergovision.statistics.interfaces.rest.resources;

public record CreateMonthlyProgressResource(
        String month,
        Double averageScore,
        Long statisticsId
) {
}
