package com.upc.quadrapp.iam.interfaces.rest.transform;

import com.upc.quadrapp.iam.domain.model.aggregates.User;
import com.upc.quadrapp.iam.interfaces.rest.resources.AuthenticatedUserResource;

public class AuthenticatedUserResourceFromEntityAssembler {
    public static AuthenticatedUserResource toResourceFromEntity(User entity, String accessToken, String refreshToken, int expiresIn){
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(entity);
        return new AuthenticatedUserResource(userResource, accessToken, refreshToken, expiresIn);
    }
}
