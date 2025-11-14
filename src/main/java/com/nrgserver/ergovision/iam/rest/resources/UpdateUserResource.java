package com.nrgserver.ergovision.iam.rest.resources;

public record UpdateUserResource(
        String username,
        String email,
        String imageUrl,
        Integer age,
        Integer height,
        Integer weight,
        String password
) {
    public UpdateUserResource {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or blank");
        }
    }
}
