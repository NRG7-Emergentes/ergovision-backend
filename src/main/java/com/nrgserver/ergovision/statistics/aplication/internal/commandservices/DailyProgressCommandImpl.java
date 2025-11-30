package com.nrgserver.ergovision.statistics.aplication.internal.commandservices;

import com.nrgserver.ergovision.statistics.domain.model.commands.CreateDailyProgressCommand;
import com.nrgserver.ergovision.statistics.domain.model.commands.CreateOnlyDailyProgressCommand;
import com.nrgserver.ergovision.statistics.domain.model.commands.DeleteDailyProgressCommand;
import com.nrgserver.ergovision.statistics.domain.model.commands.UpdateDailyProgressCommand;
import com.nrgserver.ergovision.statistics.domain.model.entities.DailyProgress;
import com.nrgserver.ergovision.statistics.domain.services.DailyProgressCommandService;
import com.nrgserver.ergovision.statistics.domain.services.MonthlyProgressQueryService;
import com.nrgserver.ergovision.statistics.infrastructure.persistence.jpa.repositories.DailyProgressRepository;
import com.nrgserver.ergovision.statistics.infrastructure.persistence.jpa.repositories.StatisticsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DailyProgressCommandImpl implements DailyProgressCommandService {

    private final DailyProgressRepository dailyProgressRepository;
    private final StatisticsRepository statisticsRepository;

    public DailyProgressCommandImpl(DailyProgressRepository dailyProgressRepository, StatisticsRepository statisticsRepository) {
        this.dailyProgressRepository = dailyProgressRepository;
        this.statisticsRepository = statisticsRepository;
    }

    @Override
    public Long handle(CreateOnlyDailyProgressCommand command) {
        var onlyDailyProgress = new DailyProgress(command);
        try {
            this.dailyProgressRepository.save(onlyDailyProgress);
        } catch (Exception e) {
            throw new IllegalArgumentException("ERROR WHILE SAVING DAILY PROGRESS: " + e.getMessage());
        }
        return onlyDailyProgress.getId();
    }

    @Override
    public Long handle(CreateDailyProgressCommand command) {
        var statistics = this.statisticsRepository.findById(command.statisticsId())
                .orElseThrow(() -> new IllegalArgumentException("Statistics with ID " + command.statisticsId() + " does not exist."));

        var dailyProgress = new DailyProgress(command, statistics);
        try {
            this.dailyProgressRepository.save(dailyProgress);
        } catch (Exception e) {
            throw new IllegalArgumentException("ERROR WHILE SAVING DAILY PROGRESS: " + e.getMessage());
        }
        return dailyProgress.getId();
    }

    @Override
    public Optional<DailyProgress> handle(UpdateDailyProgressCommand command) {
        var dailyProgressId = command.dailyProgressId();
        if(!this.dailyProgressRepository.existsById(dailyProgressId)) {
            throw new IllegalArgumentException("Daily Progress with ID " + dailyProgressId + " does not exist.");
        }
        var dailyProgressToUpdate = this.dailyProgressRepository.findById(dailyProgressId).get();
        dailyProgressToUpdate.updateDailyProgressInformation(
                command.date(),
                command.averageScore());
        try{
            var updatedDailyProgress = this.dailyProgressRepository.save(dailyProgressToUpdate);
            return Optional.of(updatedDailyProgress);
        } catch (Exception e){
            throw new IllegalArgumentException("ERROR WHILE Saving DAILY PROGRESS: " + e.getMessage());
        }

    }

    @Override
    public void handle(DeleteDailyProgressCommand command) {
        if (!this.dailyProgressRepository.existsById(command.dailyProgressId())) {
            throw new IllegalArgumentException("Daily Progress with ID " + command.dailyProgressId() + " does not exist.");
        }
        try {
            this.dailyProgressRepository.deleteById(command.dailyProgressId());
        } catch (Exception e) {
            throw new IllegalArgumentException("ERROR WHILE DELETING DAILY PROGRESS: " + e.getMessage());
        }

    }
}
