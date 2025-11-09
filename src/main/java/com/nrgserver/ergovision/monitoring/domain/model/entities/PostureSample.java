package com.nrgserver.ergovision.monitoring.domain.model.entities;

import com.nrgserver.ergovision.monitoring.domain.model.aggregates.MonitoringSession;
import com.nrgserver.ergovision.monitoring.domain.model.valueobjects.PostureStatus;
import com.nrgserver.ergovision.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "posture_sample")
@Access(AccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
public class PostureSample extends AuditableModel {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "monitoring_session_id", nullable = false)
    private MonitoringSession monitoringSession;

    @Column(name = "recorded_at", nullable = false)
    private Instant recordedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "posture_status", nullable = false, length = 50)
    private PostureStatus postureStatus;// GOOD, BAD
}