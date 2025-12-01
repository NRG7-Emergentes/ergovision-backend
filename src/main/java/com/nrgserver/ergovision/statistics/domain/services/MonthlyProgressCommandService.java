package com.nrgserver.ergovision.statistics.domain.services;

import com.nrgserver.ergovision.statistics.domain.model.commands.*;
import com.nrgserver.ergovision.statistics.domain.model.entities.MonthlyProgress;

import java.util.Optional;

public interface MonthlyProgressCommandService {
    Long handle(CreateOnlyMonthlyProgressCommand command);
    Long handle(CreateMonthlyProgressCommand command);
    Optional<MonthlyProgress> handle(UpdateMonthlyProgressCommand command);
    void handle(DeleteMonthlyProgressCommand command);
    void handle(DeleteMonthlyProgressByStatisticsIdCommand command);
}
