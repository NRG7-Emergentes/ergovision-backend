package com.nrgserver.ergovision.iam.rest.resources;


import com.nrgserver.ergovision.iam.domain.model.valueobjects.Roles;

import java.util.List;

public record UserResource(
        Long id,
        String username,
        String email,
        String imageUrl,
        Integer age,
        Integer height,
        Integer weight,
        List<Roles> roles
) { }
