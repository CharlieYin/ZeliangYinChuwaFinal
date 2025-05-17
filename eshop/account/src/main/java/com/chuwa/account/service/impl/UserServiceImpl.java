package com.chuwa.account.service.impl;

import com.chuwa.account.dao.UserRepository;
import com.chuwa.account.entity.User;
import com.chuwa.account.exception.UserExistsException;
import com.chuwa.account.exception.UserNotFoundException;
import com.chuwa.account.payload.UserDto;
import com.chuwa.account.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) throw new UserExistsException(userDto.getEmail());
        User user = modelMapper.map(userDto, User.class);
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updateUserByEmail(String email, UserDto userDto) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        if (userDto.getFirstName() != null) user.setFirstName(userDto.getFirstName());
        if (userDto.getLastName() != null) user.setLastName(userDto.getLastName());
        if (userDto.getPassword() != null) {
            String encodedPassword = encoder.encode(userDto.getPassword());
            user.setPassword(encodedPassword);
        }
        if (userDto.getShipAddr() != null) user.setShipAddr(userDto.getShipAddr());
        if (userDto.getBillAddr() != null) user.setBillAddr(userDto.getBillAddr());
        if (userDto.getPaymentMethod() != null) user.setPaymentMethod(userDto.getPaymentMethod());
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public void deleteUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        userRepository.delete(user);
    }
}
