package com.nrgserver.ergovision.orchestrator.interfaces.rest.transform;

import com.nrgserver.ergovision.orchestrator.domain.model.commands.UpdateCalibrationDetailsCommand;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.resources.UpdateCalibrationDetailsResource;

public class UpdateCalibrationDetailsCommandFromResourceAssembler {
    public static UpdateCalibrationDetailsCommand toCommandFromResource (Long calibrationId, UpdateCalibrationDetailsResource resource) {
        return new UpdateCalibrationDetailsCommand(
                calibrationId,
                resource.calibrationScore(),
                resource.cameraDistance(),
                resource.cameraVisibility(),
                resource.shoulderAngle(),
                resource.headAngle()
        );
    }
}
