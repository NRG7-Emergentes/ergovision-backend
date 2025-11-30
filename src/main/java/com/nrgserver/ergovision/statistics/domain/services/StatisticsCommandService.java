package com.nrgserver.ergovision.statistics.domain.services;

import com.nrgserver.ergovision.statistics.domain.model.aggregates.Statistics;
import com.nrgserver.ergovision.statistics.domain.model.commands.CreateStatisticsCommand;
import com.nrgserver.ergovision.statistics.domain.model.commands.DeleteStatisticsCommand;
import com.nrgserver.ergovision.statistics.domain.model.commands.UpdateStatisticsCommand;

import java.util.Optional;

public interface StatisticsCommandService {
    Long handle(CreateStatisticsCommand command);
    Optional<Statistics> handle(UpdateStatisticsCommand command);
    void handle(DeleteStatisticsCommand command);
}
