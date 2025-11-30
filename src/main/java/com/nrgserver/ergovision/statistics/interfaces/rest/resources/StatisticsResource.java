package com.nrgserver.ergovision.statistics.interfaces.rest.resources;

import java.util.List;

public record StatisticsResource(
        Long id,
        Long userId,
        List<OnlyMonthlyProgressResource> monthlyProgresses,
        List<OnlyDailyProgressResource> dailyProgresses,
        Double globalAverageScore,
        Double averageSessionTimeMinutes,
        Double averagePausesPerSession,
        Double totalMonitoredHours
) {
}
