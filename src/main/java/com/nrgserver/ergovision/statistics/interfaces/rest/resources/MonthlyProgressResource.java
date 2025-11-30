package com.nrgserver.ergovision.statistics.interfaces.rest.resources;

public record MonthlyProgressResource(
        Long id,
        String month,
        Double averageScore,
        Long statisticsId
) {
}
