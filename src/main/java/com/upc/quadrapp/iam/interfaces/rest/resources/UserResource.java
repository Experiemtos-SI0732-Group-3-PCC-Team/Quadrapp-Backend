package com.upc.quadrapp.iam.interfaces.rest.resources;

import java.time.LocalDateTime;
import java.util.List;

public record UserResource(
    Long id,
    String email,
    String firstName,
    String lastName,
    List<String> roles,
    boolean isEmailVerified,
    LocalDateTime createdAt,
    LocalDateTime lastLoginAt,
    String plan
) {
}
