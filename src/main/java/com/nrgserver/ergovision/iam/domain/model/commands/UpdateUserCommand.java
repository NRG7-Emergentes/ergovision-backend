package com.nrgserver.ergovision.iam.domain.model.commands;

public record UpdateUserCommand(
       // String username,
       // String password,
        String name,
        String lastName,
        Integer age,
        Double height,
        Double weight,
        String imageUrl
        ) {
    public UpdateUserCommand{
       /* if (username==null || username.isBlank() ) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (password==null || password.isBlank() ) {
            throw new IllegalArgumentException("Password cannot be empty");
        }*/
    }
}
