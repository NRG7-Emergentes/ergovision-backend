package com.nrgserver.ergovision.statistics.interfaces.rest.transform;

import com.nrgserver.ergovision.statistics.domain.model.aggregates.Statistics;
import com.nrgserver.ergovision.statistics.interfaces.rest.resources.StatisticsResource;

public class StatisticsResourceFromEntityAssembler {
    public static StatisticsResource toResourceFromEntity(Statistics entity){
        return new StatisticsResource(
                entity.getId(),
                entity.getUserId(),
                entity.getMonthlyProgress().stream()
                        .map(MonthlyProgressResourceFromEntityAssembler::toOnlyResourceFromEntity)
                        .toList(),
                entity.getDailyProgress().stream()
                        .map(DailyProgressResourceFromEntityAssembler::toOnlyResourceFromEntity)
                        .toList(),
                entity.getGlobalAverageScore(),
                entity.getAverageSessionTimeMinutes(),
                entity.getAveragePausesPerSession(),
                entity.getTotalMonitoredHours()

        );
    }
}
