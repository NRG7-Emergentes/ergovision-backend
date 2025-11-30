package com.nrgserver.ergovision.statistics.interfaces.rest.transform;

import com.nrgserver.ergovision.statistics.domain.model.commands.CreateDailyProgressCommand;
import com.nrgserver.ergovision.statistics.domain.model.commands.CreateOnlyDailyProgressCommand;
import com.nrgserver.ergovision.statistics.interfaces.rest.resources.CreateDailyProgressResource;
import com.nrgserver.ergovision.statistics.interfaces.rest.resources.CreateOnlyDailyProgressResource;

public class CreateDailyProgressCommandFromResourceAssembler {

    public static CreateDailyProgressCommand toCommandFromResource(CreateDailyProgressResource resource){
        return new CreateDailyProgressCommand(
                resource.date(),
                resource.averageScore(),
                resource.statisticsId()
        );
    }

    public static CreateOnlyDailyProgressCommand toCommandOnlyFromResource(CreateOnlyDailyProgressResource resource){
        return new CreateOnlyDailyProgressCommand(
                resource.date(),
                resource.averageScore()
        );
    }

}
