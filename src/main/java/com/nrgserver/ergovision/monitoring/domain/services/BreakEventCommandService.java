package com.nrgserver.ergovision.monitoring.domain.services;

import com.nrgserver.ergovision.monitoring.domain.model.commands.CreateBreakEventCommand;
import com.nrgserver.ergovision.monitoring.domain.model.commands.DeleteBreakEventCommand;
import com.nrgserver.ergovision.monitoring.domain.model.commands.UpdateBreakEventCommand;
import com.nrgserver.ergovision.monitoring.domain.model.entities.BreakEvent;

import java.util.Optional;

public interface BreakEventCommandService {
    Long handle(CreateBreakEventCommand command);
    Optional<BreakEvent> handle(UpdateBreakEventCommand command);
    void handle(DeleteBreakEventCommand command);
}
