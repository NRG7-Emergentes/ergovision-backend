package com.nrgserver.ergovision.orchestrator.interfaces.rest.transform;

import com.nrgserver.ergovision.orchestrator.domain.model.aggregates.CalibrationDetails;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.resources.CalibrationDetailsResource;

public class CalibrationDetailsResourceFromEntityAssembler {
    public static CalibrationDetailsResource toResourceFromEntity (CalibrationDetails entity) {
        return new CalibrationDetailsResource(
                entity.getId(),
                entity.getUserId(),
                entity.getCalibrationScore(),
                entity.getCameraDistance(),
                entity.getCameraVisibility(),
                entity.getShoulderAngle(),
                entity.getHeadAngle(),
                entity.getUpdatedAt().toString()
        );
    }
}
