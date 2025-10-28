package com.nrgserver.ergovision.iam.rest.resources;

import java.util.List;

public record AuthenticatedUserResource(
        Long id,
        String username,
        String token,
        List<String> roles
) {
}
