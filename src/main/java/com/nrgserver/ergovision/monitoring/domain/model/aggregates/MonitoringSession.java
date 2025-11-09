package com.nrgserver.ergovision.monitoring.domain.model.aggregates;


import com.nrgserver.ergovision.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "monitoring_session")
public class MonitoringSession extends AuditableAbstractAggregateRoot<MonitoringSession> {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "start_at")
    private Instant startAt;

    @Column(name = "end_at")
    private Instant endAt;

}