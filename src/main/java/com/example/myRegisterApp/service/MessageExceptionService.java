package com.example.myRegisterApp.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

/**
 * Service for retrieving localized error messages.
 */
@Service
public class MessageExceptionService {
    private final MessageSource messageSource;

    public MessageExceptionService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Retrieves an error message based on the provided code.
     *
     * @param code the error message code
     * @return the localized error message
     */
    public String getErrorMessage(String code) {
        return messageSource.getMessage(code, null, null);
    }
}
