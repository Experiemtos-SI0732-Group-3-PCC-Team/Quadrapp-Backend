package com.upc.quadrapp.iam.infrastructure.hashing.bcrypt;

import com.upc.quadrapp.iam.application.internal.outboundservices.hashing.HashingService;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface BCryptHashingService extends HashingService, PasswordEncoder {
}
