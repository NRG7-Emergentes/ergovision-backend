package com.nrgserver.ergovision.iam.rest.transform;


import com.nrgserver.ergovision.iam.domain.model.aggregates.User;
import com.nrgserver.ergovision.iam.domain.model.entities.Role;
import com.nrgserver.ergovision.iam.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User user) {
        return new UserResource(
                user.getId(),
                user.getUsername(),
                user.getUserRoles().stream().map(Role::getName).toList()
        );
    }

}
