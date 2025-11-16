package com.upc.quadrapp.iam.interfaces.rest.resources;

public record AuthenticatedUserResource(
    UserResource user,
    String accessToken,
    String refreshToken,
    int expiresIn
) {
}
