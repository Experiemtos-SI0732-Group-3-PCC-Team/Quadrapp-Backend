package com.upc.quadrapp.iam.domain.model.commands;

import com.upc.quadrapp.iam.domain.model.entities.Role;

import java.util.List;

public record SignUpCommand(String username, String password, List<Role> roles) {
}
