package com.deepak.techstore.user.mapper;

import com.deepak.techstore.auth.dto.request.RegisterRequest;
import com.deepak.techstore.user.dto.response.UserResponse;
import com.deepak.techstore.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);
//    User toUser(RegisterRequest request);
}
