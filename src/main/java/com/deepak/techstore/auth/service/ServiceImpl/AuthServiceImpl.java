package com.deepak.techstore.auth.service.ServiceImpl;

import com.deepak.techstore.auth.dto.request.LoginRequest;
import com.deepak.techstore.auth.dto.request.RegisterRequest;
import com.deepak.techstore.auth.dto.response.LoginResponse;
import com.deepak.techstore.auth.dto.response.RegisterResponse;
import com.deepak.techstore.auth.jwt.JwtService;
import com.deepak.techstore.auth.service.AuthService;
import com.deepak.techstore.common.constant.ErrorMessages;
import com.deepak.techstore.security.CustomUserDetails;
import com.deepak.techstore.user.dto.response.UserResponse;
import com.deepak.techstore.user.entity.User;
import com.deepak.techstore.user.exception.EmailAlreadyExistsException;
import com.deepak.techstore.user.mapper.UserMapper;
import com.deepak.techstore.user.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UserMapper userMapper
            ){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
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
//        User user = userMapper.toUser(request);
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

    @Override
    public LoginResponse login(LoginRequest request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);
//        UserResponse userResponse = UserResponse.builder()
//                .id(user.getId())
//                .firstName(user.getFirstName())
//                .lastName(user.getLastName())
//                .email(user.getEmail())
//                .phoneNumber(user.getPhoneNumber())
//                .build();
//        UserResponse userResponse = userMapper.toUserResponse(userDetails.getUser());
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiresAt(jwtService.getAccessTokenExpirationTime())
                .refreshTokenExpiresAt(jwtService.getRefreshTokenExpirationTime())
                .user(userMapper.toUserResponse(userDetails.getUser()))
                .build();
    }
}
