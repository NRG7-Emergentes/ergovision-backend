package com.nrgserver.ergovision.iam.rest.transform;


import com.nrgserver.ergovision.iam.domain.model.commands.SignInCommand;
import com.nrgserver.ergovision.iam.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(SignInResource signInResource){
        return new SignInCommand(
                signInResource.username(),
                signInResource.password()
        );
    }
}
