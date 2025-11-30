package com.nrgserver.ergovision.statistics.interfaces.rest.resources;

public record CreateOnlyMonthlyProgressResource(
        String month,
        Double averageScore
) {
}
