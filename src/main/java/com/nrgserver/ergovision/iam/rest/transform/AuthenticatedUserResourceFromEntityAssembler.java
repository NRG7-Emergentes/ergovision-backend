package com.nrgserver.ergovision.iam.rest.transform;


import com.nrgserver.ergovision.iam.domain.model.aggregates.User;
import com.nrgserver.ergovision.iam.rest.resources.AuthenticatedUserResource;

public class AuthenticatedUserResourceFromEntityAssembler {
    public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
        return new AuthenticatedUserResource(
                user.getId(),
                user.getUsername(),
                user.getImageUrl(),
                token,
                user.getUserRoles().stream().map(
                        role -> role.getName().name()
                ).toList()
        );
    }
}
