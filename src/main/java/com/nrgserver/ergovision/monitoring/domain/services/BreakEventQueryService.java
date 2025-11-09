package com.nrgserver.ergovision.monitoring.domain.services;

import com.nrgserver.ergovision.monitoring.domain.model.entities.BreakEvent;
import com.nrgserver.ergovision.monitoring.domain.model.queries.GetBreakEventByIdQuery;

import java.util.Optional;

public interface BreakEventQueryService {
    Optional<BreakEvent> handle(GetBreakEventByIdQuery query);
}
