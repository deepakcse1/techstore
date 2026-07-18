package com.deepak.techstore.auth.dto.response;

import com.deepak.techstore.user.dto.response.UserResponse;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    private String accessToken;

    private String refreshToken;

    private Instant accessTokenExpiresAt;

    private Instant refreshTokenExpiresAt;

    private UserResponse user;
}
