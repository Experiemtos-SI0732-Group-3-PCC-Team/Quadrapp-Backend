package com.upc.quadrapp.iam.domain.services;

import com.upc.quadrapp.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
    void handle(SeedRolesCommand command);
}
