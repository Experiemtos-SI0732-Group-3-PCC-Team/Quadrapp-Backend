package com.upc.quadrapp.iam.interfaces.rest.resources;

public record SignUpResource(
    String email,
    String password,
    String firstName,
    String lastName,
    Boolean acceptTerms,
    String role  // opcional: "owner" o null
) {
}
