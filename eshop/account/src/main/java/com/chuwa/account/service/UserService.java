package com.chuwa.account.service;

import com.chuwa.account.payload.UserDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public interface UserService {
    UserDto createUser(UserDto userDto);
    public UserDto getUserByEmail(String email);
    UserDto updateUserByEmail(String email, UserDto userDto);
    void deleteUserByEmail(String email);
}
