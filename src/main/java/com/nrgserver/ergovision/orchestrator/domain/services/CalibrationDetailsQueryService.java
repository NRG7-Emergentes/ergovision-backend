package com.nrgserver.ergovision.orchestrator.domain.services;

import com.nrgserver.ergovision.orchestrator.domain.model.aggregates.CalibrationDetails;
import com.nrgserver.ergovision.orchestrator.domain.model.queries.GetCalibrationDetailsByIdQuery;
import com.nrgserver.ergovision.orchestrator.domain.model.queries.GetUserCalibrationDetailsQuery;

import java.util.Optional;

public interface CalibrationDetailsQueryService {
    Optional<CalibrationDetails> handle(GetUserCalibrationDetailsQuery query);

    Optional<CalibrationDetails> handle(GetCalibrationDetailsByIdQuery query);
}
