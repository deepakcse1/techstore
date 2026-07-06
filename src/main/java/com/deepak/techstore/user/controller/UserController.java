package com.deepak.techstore.user.controller;

import com.deepak.techstore.common.dto.ApiResponse;
import com.deepak.techstore.user.dto.request.RegisterRequest;
import com.deepak.techstore.user.dto.response.UserResponse;
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
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService){
        this.userService =  userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@Valid @RequestBody RegisterRequest request){
        UserResponse userResponse = userService.register(request);
        ApiResponse<UserResponse> apiResponse = ApiResponse.<UserResponse>builder()
                .success(true)
                .message("User Registered Successfully")
                .timestamp(Instant.now())
                .data(userResponse)
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(apiResponse);
    }
}
