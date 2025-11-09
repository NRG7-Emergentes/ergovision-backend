package com.nrgserver.ergovision.monitoring.domain.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nrgserver.ergovision.monitoring.domain.model.aggregates.MonitoringSession;
import com.nrgserver.ergovision.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "break_event")
@Access(AccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
public class BreakEvent extends AuditableModel {

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "monitoring_session_id", nullable = false)
    private MonitoringSession monitoringSession;

    @Column(name = "started_at", nullable = false)
    private Instant startedAt; // inicio de la pausa

    @Column(name = "ended_at")
    private Instant endedAt; // cuando finaliza la pausa
}