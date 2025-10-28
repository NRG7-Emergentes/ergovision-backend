package com.nrgserver.ergovision.iam.rest.transform;


import com.nrgserver.ergovision.iam.domain.model.entities.Role;
import com.nrgserver.ergovision.iam.rest.resources.RoleResource;

public class RoleResourceFromEntityAssembler {
    public static RoleResource toResourceFromEntity(Role roleEntity) {

        return new RoleResource(
                roleEntity.getId(),
                roleEntity.getStringName()
        );
    }
}
