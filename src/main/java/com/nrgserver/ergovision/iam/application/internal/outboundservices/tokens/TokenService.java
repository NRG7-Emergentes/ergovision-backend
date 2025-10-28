package com.nrgserver.ergovision.iam.application.internal.outboundservices.tokens;

public interface TokenService {
    String generateToken(String userName);

    String getUserNameFromToken(String token);

    boolean validateToken(String token);
}
