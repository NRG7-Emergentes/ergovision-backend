package com.nrgserver.ergovision.orchestrator.application.internal.queryservices;

import com.nrgserver.ergovision.orchestrator.domain.model.aggregates.CalibrationDetails;
import com.nrgserver.ergovision.orchestrator.domain.model.queries.GetCalibrationDetailsByIdQuery;
import com.nrgserver.ergovision.orchestrator.domain.model.queries.GetUserCalibrationDetailsQuery;
import com.nrgserver.ergovision.orchestrator.domain.services.CalibrationDetailsQueryService;
import com.nrgserver.ergovision.orchestrator.infrastructure.persistence.jpa.repositories.CalibrationDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CalibrationDetailsQueryServiceImpl implements CalibrationDetailsQueryService {

    private final CalibrationDetailsRepository calibrationDetailsRepository;

    public CalibrationDetailsQueryServiceImpl(CalibrationDetailsRepository calibrationDetailsRepository) {
        this.calibrationDetailsRepository = calibrationDetailsRepository;
    }

    @Override
    public Optional<CalibrationDetails> handle(GetUserCalibrationDetailsQuery query) {
        return calibrationDetailsRepository.findByUserId(query.userId());
    }

    @Override
    public Optional<CalibrationDetails> handle(GetCalibrationDetailsByIdQuery query) {
        return calibrationDetailsRepository.findById(query.calibrationDetailsId());
    }
}
