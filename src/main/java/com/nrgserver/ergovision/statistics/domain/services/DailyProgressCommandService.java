package com.nrgserver.ergovision.statistics.domain.services;

import com.nrgserver.ergovision.statistics.domain.model.commands.CreateDailyProgressCommand;
import com.nrgserver.ergovision.statistics.domain.model.commands.CreateOnlyDailyProgressCommand;
import com.nrgserver.ergovision.statistics.domain.model.commands.DeleteDailyProgressCommand;
import com.nrgserver.ergovision.statistics.domain.model.commands.UpdateDailyProgressCommand;
import com.nrgserver.ergovision.statistics.domain.model.entities.DailyProgress;

import java.util.Optional;

public interface DailyProgressCommandService {
    Long handle(CreateOnlyDailyProgressCommand command);
    Long handle(CreateDailyProgressCommand command);
    Optional<DailyProgress> handle(UpdateDailyProgressCommand command);
    void handle(DeleteDailyProgressCommand command);
}
