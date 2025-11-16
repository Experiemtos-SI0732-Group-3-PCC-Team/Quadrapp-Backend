package com.upc.quadrapp.iam.interfaces.rest.transform;

import com.upc.quadrapp.iam.domain.model.aggregates.User;
import com.upc.quadrapp.iam.domain.model.entities.Role;
import com.upc.quadrapp.iam.interfaces.rest.resources.UserResource;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User entity) {
        var roles = entity.getRoles().stream().map(Role::getStringName).toList();

        // Convertir Date a LocalDateTime
        LocalDateTime createdAt = entity.getCreatedAt() != null
            ? entity.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
            : null;

        return new UserResource(
            entity.getId(),
            entity.getEmail(),
            entity.getFirstName(),
            entity.getLastName(),
            roles,
            entity.isEmailVerified(),
            createdAt,
            entity.getLastLoginAt(),
            entity.getPlan()
        );
    }
}
