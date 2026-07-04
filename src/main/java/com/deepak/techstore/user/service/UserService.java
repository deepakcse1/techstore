package com.deepak.techstore.user.service;

import com.deepak.techstore.user.dto.request.RegisterRequest;
import com.deepak.techstore.user.dto.response.UserResponse;
import org.springframework.stereotype.Service;

public interface UserService {
    UserResponse register(RegisterRequest request);
}
