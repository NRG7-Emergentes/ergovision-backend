package com.nrgserver.ergovision.iam.rest.resources;


import com.nrgserver.ergovision.iam.domain.model.valueobjects.Roles;

import java.util.List;

public record UserResource(
        Long id,
        String username,
        List<Roles> roles
) { }
