package com.nrgserver.ergovision.statistics.interfaces.rest.resources;

public record CreateOnlyDailyProgressResource(
        String date,
        Double averageScore
) {
}
