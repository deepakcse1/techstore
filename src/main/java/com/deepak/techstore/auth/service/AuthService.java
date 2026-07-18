package com.deepak.techstore.auth.service;

import com.deepak.techstore.auth.dto.request.LoginRequest;
import com.deepak.techstore.auth.dto.request.RegisterRequest;
import com.deepak.techstore.auth.dto.response.LoginResponse;
import com.deepak.techstore.auth.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
}
