package com.nrgserver.ergovision.statistics.interfaces.rest.transform;

import com.nrgserver.ergovision.statistics.domain.model.commands.CreateMonthlyProgressCommand;
import com.nrgserver.ergovision.statistics.domain.model.commands.CreateOnlyMonthlyProgressCommand;
import com.nrgserver.ergovision.statistics.interfaces.rest.resources.CreateMonthlyProgressResource;
import com.nrgserver.ergovision.statistics.interfaces.rest.resources.CreateOnlyMonthlyProgressResource;

public class CreateMonthlyProgressCommandFromResourceAssembler {
    public static CreateMonthlyProgressCommand toCommandFromResource(CreateMonthlyProgressResource resource) {
        return new CreateMonthlyProgressCommand(
                resource.month(),
                resource.averageScore(),
                resource.statisticsId()
        );
    };

    public static CreateOnlyMonthlyProgressCommand toCommandOnlyFromResource(CreateOnlyMonthlyProgressResource resource){
        return new CreateOnlyMonthlyProgressCommand(
                resource.month(),
                resource.averageScore()
        );
    }
}


