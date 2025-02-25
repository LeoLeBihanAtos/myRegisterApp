package com.example.myRegisterApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnderageUserException extends RuntimeException {
    public UnderageUserException(String message) {
        super(message);
    }
}

