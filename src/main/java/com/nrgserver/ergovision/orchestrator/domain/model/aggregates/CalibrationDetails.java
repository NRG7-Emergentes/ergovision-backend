package com.nrgserver.ergovision.orchestrator.domain.model.aggregates;

import com.nrgserver.ergovision.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CalibrationDetails extends AuditableAbstractAggregateRoot<CalibrationDetails> {
    @Column(nullable = false)
    private Long userId;

    private Long calibrationScore;

    private Long cameraDistance;

    private Long cameraVisibility;

    private Long shoulderAngle;

    private Long headAngle;

    protected CalibrationDetails() {
    }

    public CalibrationDetails(Long userId) {
        this.userId = userId;
        this.calibrationScore = 0L;
        this.cameraDistance = 0L;
        this.cameraVisibility = 0L;
        this.shoulderAngle = 0L;
        this.headAngle = 0L;
    }

    public CalibrationDetails(Long userId, Long calibrationScore, Long cameraDistance, Long cameraVisibility, Long shoulderAngle, Long headAngle) {
        this.userId = userId;
        this.calibrationScore = calibrationScore;
        this.cameraDistance = cameraDistance;
        this.cameraVisibility = cameraVisibility;
        this.shoulderAngle = shoulderAngle;
        this.headAngle = headAngle;
    }

    public void updateCalibrationDetails(Long calibrationScore, Long cameraDistance, Long cameraVisibility, Long shoulderAngle, Long headAngle) {
        if(calibrationScore != null) {
            this.calibrationScore = calibrationScore;
        }
        if(cameraDistance != null) {
            this.cameraDistance = cameraDistance;
        }
        if(cameraVisibility != null) {
            this.cameraVisibility = cameraVisibility;
        }
        if(shoulderAngle != null) {
            this.shoulderAngle = shoulderAngle;
        }
        if(headAngle != null) {
            this.headAngle = headAngle;
        }
    }
}
