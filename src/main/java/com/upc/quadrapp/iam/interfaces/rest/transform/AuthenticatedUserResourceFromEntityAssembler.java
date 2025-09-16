package com.upc.quadrapp.iam.interfaces.rest.transform;

import com.upc.quadrapp.iam.domain.model.aggregates.User;
import com.upc.quadrapp.iam.interfaces.rest.resources.AuthenticatedUserResource;

public class AuthenticatedUserResourceFromEntityAssembler {
    public static AuthenticatedUserResource toResourceFromEntity(User entity, String token){
        return new AuthenticatedUserResource(entity.getId(), entity.getUsername(), token);
    }
}
