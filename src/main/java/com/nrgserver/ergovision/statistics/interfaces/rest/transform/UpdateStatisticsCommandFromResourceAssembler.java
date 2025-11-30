package com.nrgserver.ergovision.statistics.interfaces.rest.transform;

import com.nrgserver.ergovision.statistics.domain.model.commands.UpdateStatisticsCommand;
import com.nrgserver.ergovision.statistics.interfaces.rest.resources.StatisticsResource;

public class UpdateStatisticsCommandFromResourceAssembler {
    public static UpdateStatisticsCommand toCommandFromResource(Long statisticsId, StatisticsResource resource) {
        return new UpdateStatisticsCommand(
                statisticsId,
                resource.userId(),
                resource.monthlyProgresses().stream()
                        .map(monthlyProgress -> UpdateMonthlyProgressCommandFromResourceAssembler.toCommandFromResource(
                                monthlyProgress.id(), monthlyProgress))
                        .toList(),
                resource.dailyProgresses().stream()
                        .map(dailyProgress -> UpdateDailyProgressCommandFromResourceAssembler.toCommandFromResource(
                                dailyProgress.id(), dailyProgress))
                        .toList(),
                resource.globalAverageScore(),
                resource.averageSessionTimeMinutes(),
                resource.averagePausesPerSession(),
                resource.totalMonitoredHours()
        );
    }
}
