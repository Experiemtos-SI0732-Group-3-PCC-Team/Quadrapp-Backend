package com.upc.quadrapp.iam.interfaces.rest.transform;

import com.upc.quadrapp.iam.domain.model.entities.Role;
import com.upc.quadrapp.iam.interfaces.rest.resources.RoleResource;

public class RoleResourceFromEntityAssembler {
    public static RoleResource toResourceFromEntity(Role entity) {
        return new RoleResource(entity.getId(), entity.getStringName());

    }
}
