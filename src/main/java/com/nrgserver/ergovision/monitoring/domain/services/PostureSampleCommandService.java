package com.nrgserver.ergovision.monitoring.domain.services;

import com.nrgserver.ergovision.monitoring.domain.model.commands.CreatePostureSampleCommand;
import com.nrgserver.ergovision.monitoring.domain.model.commands.DeletedPostureSampleCommand;
import com.nrgserver.ergovision.monitoring.domain.model.commands.UpdatePostureSampleCommand;
import com.nrgserver.ergovision.monitoring.domain.model.entities.PostureSample;
import com.nrgserver.ergovision.monitoring.domain.model.queries.GetPostureSampleByIdQuery;

import java.util.Optional;

public interface PostureSampleCommandService {
    Long handle(CreatePostureSampleCommand command);
    Optional<PostureSample> handle(UpdatePostureSampleCommand command);
    void handle(DeletedPostureSampleCommand command);
}
