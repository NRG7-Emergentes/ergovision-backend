package com.nrgserver.ergovision.statistics.domain.model.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nrgserver.ergovision.statistics.domain.model.aggregates.Statistics;
import com.nrgserver.ergovision.statistics.domain.model.commands.CreateMonthlyProgressCommand;
import com.nrgserver.ergovision.statistics.domain.model.commands.CreateOnlyMonthlyProgressCommand;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
public class MonthlyProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String month; // YYYY-MM
    private Double averageScore;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "statistics_id")
    @JsonBackReference
    private Statistics statistics;


    public MonthlyProgress() {
    }
    public MonthlyProgress(
            CreateOnlyMonthlyProgressCommand command
    ){
        this.month = command.month();
        this.averageScore = command.averageScore();
    }
    public MonthlyProgress(
            CreateMonthlyProgressCommand command,
            Statistics statistics
    ){
        this.month = command.month();
        this.averageScore = command.averageScore();
        this.statistics = statistics;
    }

    public void updateMonthlyProgressInformation(
            String month,
            Double averageScore) {
        this.month = month;
        this.averageScore = averageScore;
    }
}
