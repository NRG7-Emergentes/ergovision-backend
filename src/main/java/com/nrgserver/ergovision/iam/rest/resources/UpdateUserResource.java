package com.nrgserver.ergovision.iam.rest.resources;

public record UpdateUserResource(
        String email,
        String imageUrl,
        Integer age,
        Integer height,
        Integer weight
) {
}
