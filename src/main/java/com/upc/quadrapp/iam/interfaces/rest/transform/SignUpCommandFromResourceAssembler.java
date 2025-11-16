package com.upc.quadrapp.iam.interfaces.rest.transform;

import com.upc.quadrapp.iam.domain.model.commands.SignUpCommand;
import com.upc.quadrapp.iam.domain.model.entities.Role;
import com.upc.quadrapp.iam.domain.model.valueobjects.Roles;
import com.upc.quadrapp.iam.interfaces.rest.resources.SignUpResource;

import java.util.ArrayList;
import java.util.List;

public class SignUpCommandFromResourceAssembler {
    public static SignUpCommand toCommandFromResource(SignUpResource resource) {
        var roles = new ArrayList<Role>();

        // Si se proporciona un role específico (ej: "owner"), usarlo
        if (resource.role() != null && !resource.role().isEmpty()) {
            try {
                var roleName = "ROLE_" + resource.role().toUpperCase();
                roles.add(Role.toRoleFromName(roleName));
            } catch (IllegalArgumentException e) {
                // Si el rol no existe, usar el rol por defecto
                roles.add(Role.getDefaultRole());
            }
        } else {
            // Si no se proporciona role, usar el rol por defecto (ROLE_USER)
            roles.add(Role.getDefaultRole());
        }

        return new SignUpCommand(
            resource.email(),
            resource.password(),
            resource.firstName(),
            resource.lastName(),
            resource.acceptTerms() != null ? resource.acceptTerms() : false,
            roles
        );
    }
}
