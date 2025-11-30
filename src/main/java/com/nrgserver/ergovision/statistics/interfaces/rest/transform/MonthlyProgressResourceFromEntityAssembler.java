package com.nrgserver.ergovision.statistics.interfaces.rest.transform;

import com.nrgserver.ergovision.statistics.domain.model.entities.MonthlyProgress;
import com.nrgserver.ergovision.statistics.interfaces.rest.resources.MonthlyProgressResource;
import com.nrgserver.ergovision.statistics.interfaces.rest.resources.OnlyMonthlyProgressResource;

public class MonthlyProgressResourceFromEntityAssembler {
    public static MonthlyProgressResource toResourceFromEntity(MonthlyProgress entity){
        return new MonthlyProgressResource(
                entity.getId(),
                entity.getMonth(),
                entity.getAverageScore(),
                entity.getStatistics().getId()
        );
    }
    public  static OnlyMonthlyProgressResource toOnlyResourceFromEntity(MonthlyProgress entity){
        return new OnlyMonthlyProgressResource(
                entity.getId(),
                entity.getMonth(),
                entity.getAverageScore()
        );
    }
}
