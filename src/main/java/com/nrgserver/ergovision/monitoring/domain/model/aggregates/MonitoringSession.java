package com.nrgserver.ergovision.monitoring.domain.model.aggregates;

import com.nrgserver.ergovision.monitoring.domain.model.entities.BreakEvent;
import com.nrgserver.ergovision.monitoring.domain.model.entities.PostureSample;
import com.nrgserver.ergovision.monitoring.domain.model.valueobjects.SessionStatistics;
import com.nrgserver.ergovision.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "monitoring_session")
@Access(AccessType.FIELD)
public class MonitoringSession extends AuditableAbstractAggregateRoot<MonitoringSession> {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "start_at")
    private Instant startAt;

    @Column(name = "end_at")
    private Instant endAt;

    //@Embedded
    //private SessionStatistics statistics;

    @OneToMany(mappedBy = "monitoringSession", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PostureSample> samples = new ArrayList<>();

    @OneToMany(mappedBy = "monitoringSession", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BreakEvent> breakEvents = new ArrayList<>();
}