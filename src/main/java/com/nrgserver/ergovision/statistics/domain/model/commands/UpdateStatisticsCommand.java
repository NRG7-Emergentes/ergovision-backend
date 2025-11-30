package com.nrgserver.ergovision.statistics.domain.model.commands;

import java.util.List;

public record UpdateStatisticsCommand(
        Long statisticsId,
        Long userId,
        List<UpdateMonthlyProgressCommand> updateMonthlyProgressesCommand,
        List<UpdateDailyProgressCommand> updateDailyProgressesCommand,
        Double globalAverageScore,
        Double averageSessionTimeMinutes,
        Double averagePausesPerSession,
        Double totalMonitoredHours
) {
}
