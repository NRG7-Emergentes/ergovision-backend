package com.nrgserver.ergovision.statistics.interfaces.rest.transform;

import com.nrgserver.ergovision.statistics.domain.model.commands.UpdateDailyProgressCommand;
import com.nrgserver.ergovision.statistics.interfaces.rest.resources.OnlyDailyProgressResource;

public class UpdateDailyProgressCommandFromResourceAssembler {
    public static UpdateDailyProgressCommand toCommandFromResource(Long dailyProgressId, OnlyDailyProgressResource resource) {
        return new UpdateDailyProgressCommand(
                dailyProgressId,
                resource.date(),
                resource.averageScore()
        );
    }
}
