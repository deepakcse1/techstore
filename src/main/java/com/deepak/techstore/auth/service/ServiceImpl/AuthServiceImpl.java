package com.deepak.techstore.auth.service.ServiceImpl;

import com.deepak.techstore.auth.dto.request.RegisterRequest;
import com.deepak.techstore.auth.dto.response.RegisterResponse;
import com.deepak.techstore.auth.service.AuthService;
import com.deepak.techstore.common.constant.ErrorMessages;
import com.deepak.techstore.user.entity.User;
import com.deepak.techstore.user.exception.EmailAlreadyExistsException;
import com.deepak.techstore.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        //check if the email is already registered
        if(userRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException(ErrorMessages.EMAIL_ALREADY_EXISTS);
        }

        //Encoding Password
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        //Creating new Customer
        User user = User.registerCustomer(request.getFirstName(), request.getLastName(), request.getEmail(), encodedPassword, request.getPhoneNumber());

        //Saving User
        User savedUser = userRepository.save(user);

        return RegisterResponse.builder()
                .id(savedUser.getId())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .email(savedUser.getEmail())
                .phoneNumber(savedUser.getPhoneNumber())
                .build();
    }
}
