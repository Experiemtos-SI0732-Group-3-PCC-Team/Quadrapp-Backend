package com.upc.quadrapp.iam.domain.model.commands;

import com.upc.quadrapp.iam.domain.model.entities.Role;

import java.util.List;

public record SignUpCommand(
    String email,
    String password,
    String firstName,
    String lastName,
    boolean acceptTerms,
    List<Role> roles
) {
}
