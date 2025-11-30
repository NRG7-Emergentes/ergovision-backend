package com.nrgserver.ergovision.statistics.domain.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nrgserver.ergovision.statistics.domain.model.aggregates.Statistics;
import com.nrgserver.ergovision.statistics.domain.model.commands.CreateDailyProgressCommand;
import com.nrgserver.ergovision.statistics.domain.model.commands.CreateOnlyDailyProgressCommand;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DailyProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String date; //YYYY-MM-DD
    private Double averageScore;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "statistics_id")
    @JsonBackReference
    private Statistics statistics;

    public DailyProgress() {}

    public DailyProgress(
            CreateOnlyDailyProgressCommand command
    ){
        this.date = command.date();
        this.averageScore = command.averageScore();
    }

    public DailyProgress(
            CreateDailyProgressCommand command,
            Statistics statistics
    ){
        this.date = command.date();
        this.averageScore = command.averageScore();
        this.statistics = statistics;
    }

    public void updateDailyProgressInformation(
            String date,
            Double averageScore
    ) {
        this.date = date;
        this.averageScore = averageScore;
    }

}
