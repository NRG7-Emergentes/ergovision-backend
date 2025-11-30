package com.nrgserver.ergovision.statistics.domain.model.aggregates;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nrgserver.ergovision.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.nrgserver.ergovision.statistics.domain.model.commands.*;
import com.nrgserver.ergovision.statistics.domain.model.entities.DailyProgress;
import com.nrgserver.ergovision.statistics.domain.model.entities.MonthlyProgress;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "statistics")
public class Statistics extends AuditableAbstractAggregateRoot<Statistics> {

    private Long userId;

    @JsonManagedReference
    @OneToMany(mappedBy = "statistics", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MonthlyProgress> monthlyProgress = new ArrayList<>();
    @JsonManagedReference
    @OneToMany(mappedBy = "statistics", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DailyProgress> dailyProgress = new ArrayList<>();

    private Double globalAverageScore;
    private Double averageSessionTimeMinutes;
    private Double averagePausesPerSession;
    private Double totalMonitoredHours;

    public Statistics() {

    }

    public Statistics(CreateStatisticsCommand command) {
        this.userId = command.userId();
        this.monthlyProgress = new ArrayList<>();
        this.dailyProgress = new ArrayList<>();
        this.globalAverageScore = command.globalAverageScore();
        this.averageSessionTimeMinutes = command.averageSessionTimeMinutes();
        this.averagePausesPerSession = command.averagePausesPerSession();
        this.totalMonitoredHours = command.totalMonitoredHours();
        for (CreateOnlyMonthlyProgressCommand itemCommand : command.monthlyProgresses()){
            MonthlyProgress monthlyProgressItem = new MonthlyProgress(itemCommand);
            monthlyProgressItem.setStatistics(this);
            this.monthlyProgress.add(monthlyProgressItem);
        }
        for (CreateOnlyDailyProgressCommand itemCommand : command.dailyProgresses()){
            DailyProgress dailyProgressItem = new DailyProgress(itemCommand);
            dailyProgressItem.setStatistics(this);
            this.dailyProgress.add(dailyProgressItem);
        }
    }


    public void updateStatisticsInformation(
            Long userId,
            List<UpdateMonthlyProgressCommand> updateMonthlyProgressCommands,
            List<UpdateDailyProgressCommand> updateDailyProgressCommands,
            Double globalAverageScore,
            Double averageSessionTimeMinutes,
            Double averagePausesPerSession,
            Double totalMonitoredHours
    ){
        this.userId = userId;
        this.globalAverageScore = globalAverageScore;
        this.averageSessionTimeMinutes = averageSessionTimeMinutes;
        this.averagePausesPerSession = averagePausesPerSession;
        this.totalMonitoredHours = totalMonitoredHours;

       this.monthlyProgress.clear();
       this.dailyProgress.clear();

         for (UpdateMonthlyProgressCommand itemCommand : updateMonthlyProgressCommands){
             MonthlyProgress monthlyProgress = new MonthlyProgress();
                monthlyProgress.updateMonthlyProgressInformation(
                        itemCommand.month(),
                        itemCommand.averageScore()
                );
                monthlyProgress.setStatistics(this);
                this.monthlyProgress.add(monthlyProgress);
         }
            for (UpdateDailyProgressCommand itemCommand : updateDailyProgressCommands){
                DailyProgress dailyProgress = new DailyProgress();
                    dailyProgress.updateDailyProgressInformation(
                            itemCommand.date(),
                            itemCommand.averageScore()
                    );
                    dailyProgress.setStatistics(this);
                    this.dailyProgress.add(dailyProgress);
            }
    }

}
