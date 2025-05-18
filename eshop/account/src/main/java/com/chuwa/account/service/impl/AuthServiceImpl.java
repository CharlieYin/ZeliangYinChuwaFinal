package com.chuwa.account.service.impl;

import com.chuwa.account.dao.UserRepository;
import com.chuwa.account.entity.User;
import com.chuwa.account.exception.UserExistsException;
import com.chuwa.account.exception.UserNotFoundException;
import com.chuwa.account.exception.WrongPasswordException;
import com.chuwa.account.payload.AuthDto;
import com.chuwa.account.payload.LoginDto;
import com.chuwa.account.payload.UserDto;
import com.chuwa.account.security.JwtTokenProvider;
import com.chuwa.account.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

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

    @Override
    public AuthDto login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(() -> new UserNotFoundException(loginDto.getEmail()));
        if (!encoder.matches(loginDto.getPassword(), user.getPassword())) throw new WrongPasswordException();

        AuthDto authDto = new AuthDto("Login", getToken(loginDto.getEmail(), loginDto.getPassword()));
        authDto.setTokenType("JWT");
        return authDto;
    }

    @Override
    public AuthDto signup(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) throw new UserExistsException(userDto.getEmail());
        User user = modelMapper.map(userDto, User.class);
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        AuthDto authDto = new AuthDto("Signup", getToken(userDto.getEmail(), userDto.getPassword()));
        authDto.setTokenType("JWT");
        return authDto;
    }

    private String getToken(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                email, password
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProvider.generateToken(authentication);
    }
}
