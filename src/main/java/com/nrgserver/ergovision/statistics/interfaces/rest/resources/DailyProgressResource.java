package com.nrgserver.ergovision.statistics.interfaces.rest.resources;

public record DailyProgressResource(
        Long id,
        String date,
        Double averageScore,
        Long statisticsId

) {
}
