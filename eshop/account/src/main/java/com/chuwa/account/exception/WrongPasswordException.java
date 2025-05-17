package com.chuwa.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class WrongPasswordException extends RuntimeException {

    public WrongPasswordException() {
        super("Password is wrong!");
    }
}