package com.nrgserver.ergovision.orchestrator.domain.services;


import com.nrgserver.ergovision.orchestrator.domain.model.commands.CreateCalibrationDetailsCommand;
import com.nrgserver.ergovision.orchestrator.domain.model.commands.DeleteCalibrationDetailsCommand;
import com.nrgserver.ergovision.orchestrator.domain.model.commands.UpdateCalibrationDetailsCommand;

public interface CalibrationDetailsCommandService {
    Long handle(CreateCalibrationDetailsCommand command);

    Long handle(UpdateCalibrationDetailsCommand command);

    void handle(DeleteCalibrationDetailsCommand command);
}
