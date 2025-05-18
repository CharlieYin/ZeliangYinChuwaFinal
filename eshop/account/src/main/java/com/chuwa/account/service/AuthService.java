package com.chuwa.account.service;

import com.chuwa.account.payload.AuthDto;
import com.chuwa.account.payload.LoginDto;
import com.chuwa.account.payload.UserDto;

public interface AuthService {
    AuthDto login(LoginDto loginDto);

    AuthDto signup(UserDto userDto);
}
