package com.nrgserver.ergovision.statistics.interfaces.rest.resources;

public record CreateDailyProgressResource(
        String date,
        Double averageScore,
        Long statisticsId
) {
}
