package com.chuwa.account.service;

import com.chuwa.account.payload.UserDto;
import org.springframework.beans.factory.annotation.Autowired;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto getUserByEmail(String email);
    UserDto updateUserByEmail(UserDto userDto, String email);
    void deleteUserByEmail(String email);
}
