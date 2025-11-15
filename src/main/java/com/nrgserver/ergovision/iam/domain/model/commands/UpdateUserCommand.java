package com.nrgserver.ergovision.iam.domain.model.commands;

public record UpdateUserCommand(
        String email,
        String imageUrl,
        Integer age,
        Integer height,
        Integer weight) {
}
