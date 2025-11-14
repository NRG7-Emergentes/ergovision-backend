package com.nrgserver.ergovision.iam.domain.model.commands;


import com.nrgserver.ergovision.iam.domain.model.entities.Role;

import java.util.List;

public record CreateUserCommand(
        String username,
        String email,
        String imageUrl,
        Integer age,
        Integer height,
        Integer weight,
        String password,
        List<Role> roles
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
