package com.example.myRegisterApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BirthdateInFutureException extends RuntimeException {
    public BirthdateInFutureException(String message) {
        super(message);
    }
}
