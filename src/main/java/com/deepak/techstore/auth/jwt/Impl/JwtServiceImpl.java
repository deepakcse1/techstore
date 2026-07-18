package com.deepak.techstore.auth.jwt.Impl;

import com.deepak.techstore.auth.jwt.JwtProperties;
import com.deepak.techstore.auth.jwt.JwtService;
import com.deepak.techstore.common.constant.JwtClaims;
import com.deepak.techstore.security.CustomUserDetails;
import com.deepak.techstore.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.time.Clock;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {
    private final SecretKey secretKey;
    private final Duration accessTokenExpiration;
    private final Duration refreshTokenExpiration;
    private final Clock clock;
    public JwtServiceImpl(JwtProperties jwtProperties, Clock clock){
        this.clock = clock;
        this.secretKey = Keys.hmacShaKeyFor(
                jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8
                ));
        this.accessTokenExpiration = jwtProperties.getAccessTokenExpiration();
        this.refreshTokenExpiration = jwtProperties.getRefreshTokenExpiration();
    }

    @Override
    public String generateAccessToken(CustomUserDetails userDetails) {
        return generateToken(userDetails, accessTokenExpiration);
    }

    @Override
    public String generateRefreshToken(CustomUserDetails userDetails) {
        return generateToken(userDetails, refreshTokenExpiration);
    }

    @Override
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    @Override
    public boolean validateToken(String token, String username) {
        return username.equals(extractUsername(token)) && !isTokenExpired(token);
    }

    @Override
    public Instant getAccessTokenExpirationTime() {
        return Instant.now(clock).plus(this.accessTokenExpiration);
    }

    @Override
    public Instant getRefreshTokenExpirationTime() {
        return Instant.now(clock).plus(this.refreshTokenExpiration);
    }

    private Claims extractClaims(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    }

    private boolean isTokenExpired(String token){
        return Instant.now(clock).isAfter(extractClaims(token).getExpiration().toInstant());
    }

    private String generateToken(CustomUserDetails userDetails, Duration expiration){
        User user = userDetails.getUser();
        Map<String, Object> claims = Map.of(
                JwtClaims.USER_ID, user.getId(),
                JwtClaims.ROLE, user.getRole().name()
        );
        Instant now = Instant.now(clock);
        Instant expiresAt = now.plus(expiration);
        return Jwts.builder()
                .claims(claims)
                .subject(user.getEmail())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiresAt))
                .signWith(secretKey)
                .compact();
    }
}
