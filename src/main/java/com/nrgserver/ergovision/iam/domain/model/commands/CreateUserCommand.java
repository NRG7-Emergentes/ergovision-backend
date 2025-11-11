package com.nrgserver.ergovision.iam.domain.model.commands;


import com.nrgserver.ergovision.iam.domain.model.entities.Role;

import java.util.List;

public record CreateUserCommand(
        String username,
        String password,
        List<Role> roles,
        String name,
        String lastName,
        Integer age,
        Double height,
        Double weight,
        String imageUrl
) {
    public CreateUserCommand {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or blank");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or blank");
        }
        if (roles == null || roles.isEmpty()) {
            throw new IllegalArgumentException("Roles cannot be null or empty");
        }
    }
}
