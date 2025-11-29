package com.nrgserver.ergovision.orchestrator.interfaces.rest.transform;

import com.nrgserver.ergovision.orchestrator.domain.model.commands.CreateCalibrationDetailsCommand;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.resources.CreateCalibrationDetailsResource;

public class CreateCalibrationDetailsCommandFromResourceAssembler {
    public static CreateCalibrationDetailsCommand toCommandFromResource (CreateCalibrationDetailsResource resource) {
        return new CreateCalibrationDetailsCommand(
                resource.userId(),
                resource.calibrationScore(),
                resource.cameraDistance(),
                resource.cameraVisibility(),
                resource.shoulderAngle(),
                resource.headAngle()
        );
    }
}
