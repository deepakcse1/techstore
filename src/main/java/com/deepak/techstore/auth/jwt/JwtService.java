package com.deepak.techstore.auth.jwt;

import com.deepak.techstore.security.CustomUserDetails;

import java.time.Instant;

public interface JwtService {
    String generateAccessToken(CustomUserDetails userDetails);
    String generateRefreshToken(CustomUserDetails userDetails);
    String extractUsername(String token);
    boolean validateToken(String token, String username);
    Instant getAccessTokenExpirationTime();
    Instant getRefreshTokenExpirationTime();
}
