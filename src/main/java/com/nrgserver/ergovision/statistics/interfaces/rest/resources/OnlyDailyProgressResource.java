package com.nrgserver.ergovision.statistics.interfaces.rest.resources;

public record OnlyDailyProgressResource(
        Long id,
        String date,
        Double averageScore
) {
}
