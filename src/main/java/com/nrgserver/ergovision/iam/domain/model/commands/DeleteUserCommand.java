package com.nrgserver.ergovision.iam.domain.model.commands;

public record DeleteUserCommand(Long userId) {
    public DeleteUserCommand {
        if (userId==null||userId<=0) {
            throw new IllegalArgumentException("UserId cannot be empty or negative");
        }
    }
}
