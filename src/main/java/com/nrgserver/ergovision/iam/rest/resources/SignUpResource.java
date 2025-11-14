package com.nrgserver.ergovision.iam.rest.resources;


import com.nrgserver.ergovision.iam.domain.model.valueobjects.Roles;

import java.util.List;

public record SignUpResource(
        String username,
        String email,
        String imageUrl,
        Integer age,
        Integer height,
        Integer weight,
        String password,
        List<Roles> roles
) {
}
