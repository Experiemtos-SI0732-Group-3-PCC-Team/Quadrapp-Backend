package com.upc.quadrapp.iam.application.internal.outboundservices.tokens;

public interface TokenService {
    String generateToken(String username);
    String generateRefreshToken(String username);
    boolean validateToken(String token);
    String getUsernameFromToken(String token);
    int getExpirationTime(); // en segundos
}
