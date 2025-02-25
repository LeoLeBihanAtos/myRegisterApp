package com.example.myRegisterApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NonFrenchResidentException extends RuntimeException{
    public NonFrenchResidentException(String message) {
        super(message);
    }
}
