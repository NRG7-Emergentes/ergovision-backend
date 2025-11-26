package com.nrgserver.ergovision.monitoring.domain.model.aggregates;


import com.nrgserver.ergovision.monitoring.domain.model.commands.CreateMonitoringSessionCommand;
import com.nrgserver.ergovision.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;


@Getter
@Setter
@Entity
@Table(name = "monitoring_session")
public class MonitoringSession extends AuditableAbstractAggregateRoot<MonitoringSession> {

    @Column(name = "user_id")
    private Long userId;

    private Instant startDate;
    private Instant endDate;
    private Double score;
    private Double goodScore;
    private Double badScore;
    private Double goodPostureTime;
    private Double badPostureTime;
    private Double duration;
    private Integer numberOfPauses;
    private Double averagePauseDuration;

    public MonitoringSession() {}

    public MonitoringSession(CreateMonitoringSessionCommand command) {
        this.userId = command.userId();
        this.startDate = command.startDate();
        this.endDate = command.endDate();
        this.score = command.score();
        this.goodScore = command.goodScore();
        this.badScore = command.badScore();
        this.goodPostureTime = command.goodPostureTime();
        this.badPostureTime = command.badPostureTime();
        this.duration = command.duration();
        this.numberOfPauses = command.numberOfPauses();
        this.averagePauseDuration = command.averagePauseDuration();
    }

    public void updateMonitoringSessionInformation(
            Instant startDate,
            Instant endDate,
            Double score,
            Double goodScore,
            Double badScore,
            Double goodPostureTime,
            Double badPostureTime,
            Double duration,
            Integer numberOfPauses,
            Double averagePauseDuration
    ){
        this.startDate = startDate;
        this.endDate = endDate;
        this.score = score;
        this.goodScore = goodScore;
        this.badScore = badScore;
        this.goodPostureTime = goodPostureTime;
        this.badPostureTime = badPostureTime;
        this.duration = duration;
        this.numberOfPauses = numberOfPauses;
        this.averagePauseDuration = averagePauseDuration;

    }


}