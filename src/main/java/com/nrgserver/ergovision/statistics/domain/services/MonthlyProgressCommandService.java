package com.nrgserver.ergovision.statistics.domain.services;

import com.nrgserver.ergovision.statistics.domain.model.commands.CreateMonthlyProgressCommand;
import com.nrgserver.ergovision.statistics.domain.model.commands.CreateOnlyMonthlyProgressCommand;
import com.nrgserver.ergovision.statistics.domain.model.commands.DeleteMonthlyProgressCommand;
import com.nrgserver.ergovision.statistics.domain.model.commands.UpdateMonthlyProgressCommand;
import com.nrgserver.ergovision.statistics.domain.model.entities.MonthlyProgress;

import java.util.Optional;

public interface MonthlyProgressCommandService {
    Long handle(CreateOnlyMonthlyProgressCommand command);
    Long handle(CreateMonthlyProgressCommand command);
    Optional<MonthlyProgress> handle(UpdateMonthlyProgressCommand command);
    void handle(DeleteMonthlyProgressCommand command);
}
