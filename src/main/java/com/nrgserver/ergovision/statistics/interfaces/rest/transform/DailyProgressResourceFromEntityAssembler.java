package com.nrgserver.ergovision.statistics.interfaces.rest.transform;

import com.nrgserver.ergovision.statistics.domain.model.entities.DailyProgress;
import com.nrgserver.ergovision.statistics.interfaces.rest.resources.DailyProgressResource;
import com.nrgserver.ergovision.statistics.interfaces.rest.resources.OnlyDailyProgressResource;

public class DailyProgressResourceFromEntityAssembler {
    public static DailyProgressResource toResourceFromEntity(DailyProgress entity) {
        return new DailyProgressResource(
                entity.getId(),
                entity.getDate(),
                entity.getAverageScore(),
                entity.getStatistics().getId()
        );
    }
    public static OnlyDailyProgressResource toOnlyResourceFromEntity(DailyProgress entity){
        return new OnlyDailyProgressResource(
                entity.getId(),
                entity.getDate(),
                entity.getAverageScore()
        );
    }
}
