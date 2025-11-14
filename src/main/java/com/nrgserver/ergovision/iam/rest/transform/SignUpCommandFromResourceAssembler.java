package com.nrgserver.ergovision.iam.rest.transform;


import com.nrgserver.ergovision.iam.domain.model.commands.SignUpCommand;
import com.nrgserver.ergovision.iam.rest.resources.SignUpResource;

public class SignUpCommandFromResourceAssembler {
    public static SignUpCommand toCommandFromResource(SignUpResource signUpResource) {
        return new SignUpCommand(
                signUpResource.username(),
                signUpResource.email(),
                signUpResource.imageUrl(),
                signUpResource.age(),
                signUpResource.height(),
                signUpResource.weight(),
                signUpResource.password(),
                signUpResource.roles()
        );
    }
}
