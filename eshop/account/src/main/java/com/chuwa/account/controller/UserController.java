package com.chuwa.account.controller;

import com.chuwa.account.payload.UserDto;
import com.chuwa.account.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping()
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {

        logger.info("Creating user (" + userDto.getEmail() + ")");

        UserDto userResponse = userService.createUser(userDto);

        logger.info("Created user (" + userDto.getEmail() + ") successfully!");

        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {

        logger.info("Getting user (" + email + ")");

        UserDto userResponse = userService.getUserByEmail(email);

        logger.info("Got user (" + email + ") successfully!");

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PutMapping("/{email}")
    public ResponseEntity<UserDto> updateUserByEmail(@PathVariable String email, @RequestBody UserDto userDto) {

        logger.info("Updating user (" + email + ")");

        UserDto userResponse = userService.updateUserByEmail(email, userDto);

        logger.info("Updated user (" + email + ") successfully!");

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteUserByEmail(@PathVariable String email) {

        logger.info("Deleting user (" + email + ")");

        userService.deleteUserByEmail(email);

        logger.info("Deleted user (" + email + ") successfully!");

        return new ResponseEntity<>("Deleted user (" + email + ") successfully!", HttpStatus.OK);
    }

}
