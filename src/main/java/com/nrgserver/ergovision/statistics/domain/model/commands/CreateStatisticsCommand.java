package com.nrgserver.ergovision.statistics.domain.model.commands;

import java.util.List;

public record CreateStatisticsCommand(
        Long userId,
        List<CreateOnlyMonthlyProgressCommand> monthlyProgresses,
        List<CreateOnlyDailyProgressCommand> dailyProgresses,
        Double globalAverageScore,
        Double averageSessionTimeMinutes,
        Double averagePausesPerSession,
        Double totalMonitoredHours
) {

}
