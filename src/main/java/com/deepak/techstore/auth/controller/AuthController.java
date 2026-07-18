package com.deepak.techstore.auth.controller;

import com.deepak.techstore.auth.dto.request.LoginRequest;
import com.deepak.techstore.auth.dto.request.RegisterRequest;
import com.deepak.techstore.auth.dto.response.LoginResponse;
import com.deepak.techstore.auth.dto.response.RegisterResponse;
import com.deepak.techstore.auth.service.AuthService;
import com.deepak.techstore.common.dto.ApiResponse;
import com.deepak.techstore.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.Instant;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService){
        this.authService =  authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@Valid @RequestBody RegisterRequest request){
        RegisterResponse registerResponse = authService.register(request);
        ApiResponse<RegisterResponse> apiResponse = ApiResponse.<RegisterResponse>builder()
                .success(true)
                .message("User Registered Successfully")
                .timestamp(Instant.now())
                .data(registerResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(apiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request){
        LoginResponse loginResponse =  authService.login(request);
        ApiResponse<LoginResponse> apiResponse = ApiResponse.<LoginResponse>builder()
                .success(true)
                .message("Login Successfully")
                .timestamp(Instant.now())
                .data(loginResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(apiResponse);
    }
}
