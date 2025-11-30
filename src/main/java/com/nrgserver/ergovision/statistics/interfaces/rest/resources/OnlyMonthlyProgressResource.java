package com.nrgserver.ergovision.statistics.interfaces.rest.resources;

public record OnlyMonthlyProgressResource(
        Long id,
        String month,
        Double averageScore
) {

}
