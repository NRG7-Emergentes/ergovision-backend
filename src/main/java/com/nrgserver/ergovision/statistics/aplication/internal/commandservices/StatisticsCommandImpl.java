package com.nrgserver.ergovision.statistics.aplication.internal.commandservices;

import com.nrgserver.ergovision.statistics.domain.model.aggregates.Statistics;
import com.nrgserver.ergovision.statistics.domain.model.commands.CreateStatisticsCommand;
import com.nrgserver.ergovision.statistics.domain.model.commands.DeleteStatisticsCommand;
import com.nrgserver.ergovision.statistics.domain.model.commands.DeletedStatisticsByUserIdCommand;
import com.nrgserver.ergovision.statistics.domain.model.commands.UpdateStatisticsCommand;
import com.nrgserver.ergovision.statistics.domain.services.StatisticsCommandService;
import com.nrgserver.ergovision.statistics.infrastructure.persistence.jpa.repositories.DailyProgressRepository;
import com.nrgserver.ergovision.statistics.infrastructure.persistence.jpa.repositories.MonthlyProgressRepository;
import com.nrgserver.ergovision.statistics.infrastructure.persistence.jpa.repositories.StatisticsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StatisticsCommandImpl implements StatisticsCommandService {

    private final StatisticsRepository statisticsRepository;
    private final MonthlyProgressRepository monthlyProgressRepository;
    private final DailyProgressRepository dailyProgressRepository;

    public StatisticsCommandImpl(StatisticsRepository statisticsRepository,
                                 MonthlyProgressRepository monthlyProgressRepository,
                                 DailyProgressRepository dailyProgressRepository) {
        this.statisticsRepository = statisticsRepository;
        this.monthlyProgressRepository = monthlyProgressRepository;
        this.dailyProgressRepository = dailyProgressRepository;
    }


    @Override
    public Long handle(CreateStatisticsCommand command) {
        if (this.statisticsRepository.existsByUserId (command.userId())) {
            throw new IllegalArgumentException(command.userId() + " already exists");
        }
        var statistics = new Statistics(command);
        try {
            this.statisticsRepository.save(statistics);
        } catch (Exception e){
            throw new IllegalArgumentException("ERROR WHILE SAVING MONTHLY PROGRESS: " + e.getMessage());
        }
        return statistics.getId();
    }

    @Override
    public Optional<Statistics> handle(UpdateStatisticsCommand command) {
        var statisticsId = command.statisticsId();
        if (!this.statisticsRepository.existsById (statisticsId)){
            throw new IllegalArgumentException("Statistics with id " + statisticsId + " does not exist");
        }
        var statisticsToUpdate = this.statisticsRepository.findById(statisticsId).get();
        statisticsToUpdate.updateStatisticsInformation(
                // statisticsToUpdate.getUserId(), --Cuando fianlice la implementacion con Monitoring descomentar esta linea y borrar la de abajo para que el id del usuario no se pueda modificar
                command.userId(),
                command.updateMonthlyProgressesCommand(),
                command.updateDailyProgressesCommand(),
                command.globalAverageScore(),
                command.averageSessionTimeMinutes(),
                command.averagePausesPerSession(),
                command.totalMonitoredHours()
        );

        try {
            var updatedStatistics = this.statisticsRepository.save(statisticsToUpdate);
            return Optional.of(updatedStatistics);
        } catch (Exception e) {
            throw new IllegalArgumentException("ERROR WHILE UPDATING STATISTICS: " + e.getMessage());
        }

    }

    @Override
    public void handle(DeleteStatisticsCommand command) {
        if (!this.statisticsRepository.existsById (command.statisticsId())){
            throw new IllegalArgumentException("Statistics with id " + command.statisticsId() + " does not exist");
        }
        try {
            this.statisticsRepository.deleteById(command.statisticsId());
        } catch (Exception e){
            throw new IllegalArgumentException("ERROR WHILE DELETING STATISTICS: " + e.getMessage());
        }

    }

    @Override
    public void handle(DeletedStatisticsByUserIdCommand command) {
        if (!this.statisticsRepository.existsByUserId(command.userId())){
            throw new IllegalArgumentException("Statistics for user with id " + command.userId() + " does not exist");
        }
        try {
            var statistics = this.statisticsRepository.findByUserId(command.userId()).get();
            Long statisticsId = statistics.getId();
            this.monthlyProgressRepository.deleteMonthlyProgressByStatisticsId(statisticsId);
            this.dailyProgressRepository.deleteDailyProgressByStatisticsId(statisticsId);
            this.statisticsRepository.deleteById(statisticsId);
        } catch (Exception e){
            throw new IllegalArgumentException("ERROR WHILE DELETING STATISTICS BY USER ID: " + e.getMessage());
        }
    }
}
