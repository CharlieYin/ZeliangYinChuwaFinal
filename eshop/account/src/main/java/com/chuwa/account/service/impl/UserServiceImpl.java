package com.chuwa.account.service.impl;

import com.chuwa.account.dao.UserRepository;
import com.chuwa.account.entity.User;
import com.chuwa.account.payload.UserDto;
import com.chuwa.account.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return null;
    }

    @Override
    public UserDto updateUserByEmail(UserDto userDto, String email) {
        return null;
    }

    @Override
    public void deleteUserByEmail(String email) {

    }
}
