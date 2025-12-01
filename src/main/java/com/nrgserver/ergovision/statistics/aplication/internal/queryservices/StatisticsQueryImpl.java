package com.nrgserver.ergovision.statistics.aplication.internal.queryservices;

import com.nrgserver.ergovision.statistics.aplication.internal.outboundservices.acl.ExternalMonitoringSessionService;
import com.nrgserver.ergovision.statistics.domain.model.aggregates.Statistics;
import com.nrgserver.ergovision.statistics.domain.model.commands.CreateOnlyDailyProgressCommand;
import com.nrgserver.ergovision.statistics.domain.model.commands.CreateOnlyMonthlyProgressCommand;
import com.nrgserver.ergovision.statistics.domain.model.commands.CreateStatisticsCommand;
import com.nrgserver.ergovision.statistics.domain.model.commands.DeletedStatisticsByUserIdCommand;
import com.nrgserver.ergovision.statistics.domain.model.queries.GetStatisticsByIdQuery;
import com.nrgserver.ergovision.statistics.domain.model.queries.GetStatisticsByUserIdQuery;
import com.nrgserver.ergovision.statistics.domain.services.StatisticsCommandService;
import com.nrgserver.ergovision.statistics.domain.services.StatisticsQueryService;
import com.nrgserver.ergovision.statistics.infrastructure.persistence.jpa.repositories.StatisticsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StatisticsQueryImpl implements StatisticsQueryService {

    private final StatisticsRepository statisticsRepository;
    private final ExternalMonitoringSessionService externalMonitoringSessionService;
    private final StatisticsCommandService statisticsCommandService;

    public StatisticsQueryImpl(StatisticsRepository statisticsRepository,
                               ExternalMonitoringSessionService externalMonitoringSessionService,
                               StatisticsCommandService statisticsCommandService) {
        this.statisticsRepository = statisticsRepository;
        this.externalMonitoringSessionService = externalMonitoringSessionService;
        this.statisticsCommandService = statisticsCommandService;
    }

    @Override
    public Optional<Statistics> handle(GetStatisticsByIdQuery query) {
        return this.statisticsRepository.findById(query.statisticsId());
    }

    @Override
    @Transactional
    public Optional<Statistics> handle(GetStatisticsByUserIdQuery query) {
        Long userId = query.userId();


        Optional<Statistics> existingStatistics = this.statisticsRepository.findByUserId(userId);

        if (existingStatistics.isPresent()) {

            var deleteCommand = new DeletedStatisticsByUserIdCommand(userId);
            statisticsCommandService.handle(deleteCommand);
        }


        return generateStatisticsForUser(userId);
    }

    private Optional<Statistics> generateStatisticsForUser(Long userId) {

        var monthlyProgressList = externalMonitoringSessionService.fetchMonthlyProgressByUserId(userId);


        var dailyProgressList = externalMonitoringSessionService.fetchDailyProgressByUserId(userId);


        var globalAverageScore = externalMonitoringSessionService.fetchGlobalAverageScoreByUserId(userId)
                .orElse(0.0);


        var averageSessionTimeMinutes = externalMonitoringSessionService.fetchAverageSessionTimeMinutesByUserId(userId)
                .orElse(0.0);


        var averagePausesPerSession = externalMonitoringSessionService.fetchAveragePausesPerSessionByUserId(userId)
                .orElse(0.0);


        var totalMonitoredHours = externalMonitoringSessionService.fetchTotalMonitoredHoursByUserId(userId)
                .orElse(0.0);


        var monthlyProgressCommands = monthlyProgressList.stream()
                .map(dto -> new CreateOnlyMonthlyProgressCommand(dto.month(), dto.averageScore()))
                .toList();

        var dailyProgressCommands = dailyProgressList.stream()
                .map(dto -> new CreateOnlyDailyProgressCommand(dto.date(), dto.averageScore()))
                .toList();


        var createCommand = new CreateStatisticsCommand(
                userId,
                monthlyProgressCommands,
                dailyProgressCommands,
                globalAverageScore,
                averageSessionTimeMinutes,
                averagePausesPerSession,
                totalMonitoredHours
        );


        Long statisticsId = statisticsCommandService.handle(createCommand);


        return this.statisticsRepository.findById(statisticsId);
    }
}
