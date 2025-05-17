package com.chuwa.account.controller;

import com.chuwa.account.payload.UserDto;
import com.chuwa.account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto userResponse = userService.createUser(userDto);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        UserDto userResponse = userService.getUserByEmail(email);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PutMapping("/{email}")
    public ResponseEntity<UserDto> updateUserByEmail(@PathVariable String email, @RequestBody UserDto userDto) {
        UserDto userResponse = userService.updateUserByEmail(email, userDto);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteUserByEmail(@PathVariable String email) {
        userService.deleteUserByEmail(email);
        return new ResponseEntity<>("User(" + email + ") deleted successfully.", HttpStatus.OK);
    }

}
