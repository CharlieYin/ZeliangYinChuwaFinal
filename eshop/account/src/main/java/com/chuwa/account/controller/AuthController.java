package com.chuwa.account.controller;

import com.chuwa.account.payload.LoginDto;
import com.chuwa.account.payload.UserDto;
import com.chuwa.account.payload.AuthDto;
import com.chuwa.account.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<AuthDto> login(@RequestBody LoginDto loginDto) {

        logger.info("User (" + loginDto.getEmail() + ") is trying to login.");

        AuthDto authDto = authService.login(loginDto);

        logger.info("User (" + loginDto.getEmail() + ") login successfully.");

        return ResponseEntity.ok(authDto);
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthDto> signup(@RequestBody UserDto userDto) {

        logger.info("New user (" + userDto.getEmail() + ") is trying to signup.");

        AuthDto authDto = authService.signup(userDto);

        logger.info("New user (" + userDto.getEmail() + ") signup successfully.");

        return ResponseEntity.ok(authDto);
    }
}