package com.nrgserver.ergovision.statistics.interfaces.rest.transform;

import com.nrgserver.ergovision.statistics.domain.model.commands.UpdateMonthlyProgressCommand;
import com.nrgserver.ergovision.statistics.interfaces.rest.resources.OnlyMonthlyProgressResource;

public class UpdateMonthlyProgressCommandFromResourceAssembler {
    public static UpdateMonthlyProgressCommand toCommandFromResource(Long monthlyProgressId, OnlyMonthlyProgressResource resource) {
        return new UpdateMonthlyProgressCommand(
                monthlyProgressId,
                resource.month(),
                resource.averageScore()
        );
    }
}
