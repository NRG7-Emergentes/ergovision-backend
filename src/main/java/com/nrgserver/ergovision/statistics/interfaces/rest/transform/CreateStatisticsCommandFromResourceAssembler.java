package com.nrgserver.ergovision.statistics.interfaces.rest.transform;

import com.nrgserver.ergovision.statistics.domain.model.commands.CreateStatisticsCommand;
import com.nrgserver.ergovision.statistics.interfaces.rest.resources.CreateStatisticsResource;

public class CreateStatisticsCommandFromResourceAssembler {
    public static CreateStatisticsCommand toCommandFromResource(CreateStatisticsResource resource) {
        return new CreateStatisticsCommand(
                resource.userId(),
                resource.monthlyProgresses().stream().map(CreateMonthlyProgressCommandFromResourceAssembler::toCommandOnlyFromResource)
                        .toList(),
                resource.dailyProgresses().stream().map(CreateDailyProgressCommandFromResourceAssembler::toCommandOnlyFromResource)
                        .toList(),
                resource.globalAverageScore(),
                resource.averageSessionTimeMinutes(),
                resource.averagePausesPerSession(),
                resource.totalMonitoredHours()
        );
    }
}
