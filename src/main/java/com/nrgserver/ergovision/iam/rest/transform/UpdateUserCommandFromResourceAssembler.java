package com.nrgserver.ergovision.iam.rest.transform;


import com.nrgserver.ergovision.iam.domain.model.commands.UpdateUserCommand;
import com.nrgserver.ergovision.iam.rest.resources.UpdateUserResource;

public class UpdateUserCommandFromResourceAssembler {
    public static UpdateUserCommand toCommandFromResource(UpdateUserResource updateUserResource){
        return new UpdateUserCommand(
                updateUserResource.email(),
                updateUserResource.imageUrl(),
                updateUserResource.age(),
                updateUserResource.height(),
                updateUserResource.weight());
    }
}
