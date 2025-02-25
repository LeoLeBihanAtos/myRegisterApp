package com.example.myRegisterApp.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;


@Service
public class MessageExceptionService {
    private final MessageSource messageSource;

    public MessageExceptionService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getErrorMessage(String code) {
        return messageSource.getMessage(code, null, null);
    }
}
