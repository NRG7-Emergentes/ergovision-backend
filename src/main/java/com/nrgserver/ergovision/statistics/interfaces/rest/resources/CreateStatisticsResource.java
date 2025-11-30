package com.nrgserver.ergovision.statistics.interfaces.rest.resources;


import java.util.List;

public record CreateStatisticsResource(
        Long userId,
        List<CreateOnlyMonthlyProgressResource> monthlyProgresses,
        List<CreateOnlyDailyProgressResource> dailyProgresses,
        Double globalAverageScore,
        Double averageSessionTimeMinutes,
        Double averagePausesPerSession,
        Double totalMonitoredHours
) {
}
