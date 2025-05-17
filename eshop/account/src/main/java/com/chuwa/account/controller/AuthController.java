package com.chuwa.account.controller;

import com.chuwa.account.dao.UserRepository;
import com.chuwa.account.entity.User;
import com.chuwa.account.exception.UserExistsException;
import com.chuwa.account.payload.LoginDto;
import com.chuwa.account.payload.UserDto;
import com.chuwa.account.payload.security.JWTAuthResponse;
import com.chuwa.account.security.JwtTokenProvider;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto) {

        logger.info("User (" + loginDto.getEmail() + ") is trying to login.");

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // get token from tokenProvider
        String token = tokenProvider.generateToken(authentication);
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse(token);
        jwtAuthResponse.setTokenType("JWT");

        logger.info("User (" + loginDto.getEmail() + ") login successfully.");

        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDto userDto) {

        logger.info("New user (" + userDto.getEmail() + ") is trying to signup.");

        if (userRepository.existsByEmail(userDto.getEmail())) throw new UserExistsException(userDto.getEmail());
        User user = modelMapper.map(userDto, User.class);
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        User savedUser = userRepository.save(user);

        logger.info("New user (" + userDto.getEmail() + ") signup successfully.");

        return ResponseEntity.ok("New user (" + userDto.getEmail() + ") signup successfully.");
    }
}