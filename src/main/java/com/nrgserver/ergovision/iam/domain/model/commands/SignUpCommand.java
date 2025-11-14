package com.nrgserver.ergovision.iam.domain.model.commands;

import com.nrgserver.ergovision.iam.domain.model.valueobjects.Roles;

import java.util.List;

public record SignUpCommand(
        String username,
        String email,
        String imageUrl,
        Integer age,
        Integer height,
        Integer weight,
        String password,
        List<Roles> roles
) {
    public SignUpCommand {
        if (username==null||username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (password==null||password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (roles==null||roles.isEmpty()) {
            throw new IllegalArgumentException("Roles cannot be empty");
        }
    }
}
