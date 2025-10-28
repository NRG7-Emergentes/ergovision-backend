package com.nrgserver.ergovision.iam.rest.transform;

import com.nrgserver.ergovision.iam.domain.model.commands.CreateUserCommand;
import com.nrgserver.ergovision.iam.rest.resources.CreateUserResource;

public class CreateUserCommandFromResourceAssembler {
    public static CreateUserCommand toCommandFromResource(CreateUserResource createUserResource) {
        return new CreateUserCommand(
                createUserResource.username(),
                createUserResource.password(),
                createUserResource.roles().stream().toList());
    }
}
