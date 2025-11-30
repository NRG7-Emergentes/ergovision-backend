package com.nrgserver.ergovision.orchestrator.application.internal.commandservices;

import com.nrgserver.ergovision.orchestrator.domain.model.aggregates.CalibrationDetails;
import com.nrgserver.ergovision.orchestrator.domain.model.commands.CreateCalibrationDetailsCommand;
import com.nrgserver.ergovision.orchestrator.domain.model.commands.DeleteCalibrationDetailsCommand;
import com.nrgserver.ergovision.orchestrator.domain.model.commands.UpdateCalibrationDetailsCommand;
import com.nrgserver.ergovision.orchestrator.domain.services.CalibrationDetailsCommandService;
import com.nrgserver.ergovision.orchestrator.infrastructure.persistence.jpa.repositories.CalibrationDetailsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CalibrationDetailsCommandServiceImpl implements CalibrationDetailsCommandService {

    private final CalibrationDetailsRepository calibrationDetailsRepository;

    public CalibrationDetailsCommandServiceImpl(CalibrationDetailsRepository calibrationDetailsRepository) {
        this.calibrationDetailsRepository = calibrationDetailsRepository;
    }

    @Override
    @Transactional
    public Long handle(CreateCalibrationDetailsCommand command) {
       if (calibrationDetailsRepository.existsByUserId(command.userId())) {
              throw new IllegalArgumentException("Calibration details already exist for user: " + command.userId());
       }

       CalibrationDetails calibrationDetails = new CalibrationDetails(
                command.userId(),
                command.calibrationScore(),
                command.cameraDistance(),
                command.cameraVisibility(),
                command.shoulderAngle(),
                command.headAngle()
       );

       CalibrationDetails savedDetails = calibrationDetailsRepository.save(calibrationDetails);
       return savedDetails.getId();
    }

    @Override
    @Transactional
    public Long handle(UpdateCalibrationDetailsCommand command) {
        CalibrationDetails calibrationDetails = calibrationDetailsRepository.findById(command.calibrationDetailsId())
                .orElseThrow(() -> new IllegalArgumentException("Calibration details not found with id: " + command.calibrationDetailsId()));

        calibrationDetails.updateCalibrationDetails(
                command.calibrationScore(),
                command.cameraDistance(),
                command.cameraVisibility(),
                command.shoulderAngle(),
                command.headAngle()
        );

        CalibrationDetails updatedDetails = calibrationDetailsRepository.save(calibrationDetails);
        return updatedDetails.getId();
    }

    @Override
    @Transactional
    public void handle(DeleteCalibrationDetailsCommand command) {
        if(!calibrationDetailsRepository.existsById(command.calibrationDetailsId())) {
            throw new IllegalArgumentException("Calibration details not found with id: " + command.calibrationDetailsId());
        }
        calibrationDetailsRepository.deleteById(command.calibrationDetailsId());
    }
}
