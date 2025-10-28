package com.nrgserver.ergovision.iam.infrastructure.hashing.bcrypt;

import com.nrgserver.ergovision.iam.application.internal.outboundservices.hashing.HashingService;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface BCryptHashingService extends HashingService, PasswordEncoder {




}
