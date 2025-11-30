package com.nrgserver.ergovision.statistics.aplication.internal.commandservices;

import com.nrgserver.ergovision.statistics.domain.model.commands.CreateMonthlyProgressCommand;
import com.nrgserver.ergovision.statistics.domain.model.commands.CreateOnlyMonthlyProgressCommand;
import com.nrgserver.ergovision.statistics.domain.model.commands.DeleteMonthlyProgressCommand;
import com.nrgserver.ergovision.statistics.domain.model.commands.UpdateMonthlyProgressCommand;
import com.nrgserver.ergovision.statistics.domain.model.entities.MonthlyProgress;
import com.nrgserver.ergovision.statistics.domain.services.MonthlyProgressCommandService;
import com.nrgserver.ergovision.statistics.domain.services.StatisticsQueryService;
import com.nrgserver.ergovision.statistics.infrastructure.persistence.jpa.repositories.MonthlyProgressRepository;
import com.nrgserver.ergovision.statistics.infrastructure.persistence.jpa.repositories.StatisticsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MonthlyProgressCommandImpl implements MonthlyProgressCommandService {

    private final MonthlyProgressRepository monthlyProgressRepository;
    private final StatisticsRepository statisticsRepository;

    public MonthlyProgressCommandImpl(MonthlyProgressRepository monthlyProgressRepository, StatisticsRepository statisticsRepository) {
        this.monthlyProgressRepository = monthlyProgressRepository;
        this.statisticsRepository = statisticsRepository;
    }

    @Override
    public Long handle(CreateOnlyMonthlyProgressCommand command) {
        var onlyMonthlyProgress = new MonthlyProgress(command);
        try {
            this.monthlyProgressRepository.save(onlyMonthlyProgress);
        } catch (Exception e) {
            throw new IllegalArgumentException("ERROR WHILE SAVING MONTHLY PROGRESS: " + e.getMessage());

        }
        return onlyMonthlyProgress.getId();
    }

    @Override
    public Long handle(CreateMonthlyProgressCommand command) {

        var statistics = this.statisticsRepository.findById(command.statisticsId())
                .orElseThrow(()-> new IllegalArgumentException("Statistics with ID " + command.statisticsId() + " does not exist."));

        var monthlyProgress = new MonthlyProgress(command, statistics);
        try {
            this.monthlyProgressRepository.save(monthlyProgress);
        } catch (Exception e) {
            throw new IllegalArgumentException("ERROR WHILE SAVING MONTHLY PROGRESS: " + e.getMessage());
        }
        return monthlyProgress.getId();
    }

    @Override
    public Optional<MonthlyProgress> handle(UpdateMonthlyProgressCommand command) {
        var monthlyProgressId = command.monthlyProgressId();
        if(!this.monthlyProgressRepository.existsById(monthlyProgressId)) {
            throw new IllegalArgumentException("Monthly Progress with ID " + monthlyProgressId + " does not exist.");
        }
        var monthlyProgressToUpdate = this.monthlyProgressRepository.findById(monthlyProgressId).get();
        monthlyProgressToUpdate.updateMonthlyProgressInformation(
                command.month(),
                command.averageScore());
        try{
            var updatedMonthlyProgress = this.monthlyProgressRepository.save(monthlyProgressToUpdate);
            return Optional.of(updatedMonthlyProgress);
        } catch (Exception e){
            throw new IllegalArgumentException("ERROR WHILE Saving MONTHLY PROGRESS: " + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteMonthlyProgressCommand command) {
        if (!this.monthlyProgressRepository.existsById(command.monthlyProgressId())) {
            throw new IllegalArgumentException("Monthly Progress with ID " + command.monthlyProgressId() + " does not exist.");
        }
        try {
            this.monthlyProgressRepository.deleteById(command.monthlyProgressId());
        } catch (Exception e) {
            throw new IllegalArgumentException("ERROR WHILE DELETING MONTHLY PROGRESS: " + e.getMessage());
        }

    }
}
