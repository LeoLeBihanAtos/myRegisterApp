package com.example.myRegisterApp.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MessageExceptionServiceTest {

    @InjectMocks
    private MessageExceptionService messageExceptionService;

    @Mock
    private MessageSource messageSource;

    @Test
    public void testGetErrorMessage_ShouldReturnCorrectMessage() {
        String messageCode = "user.age.underage";
        String expectedMessage = "User must be of legal age (18+ years)";

        when(messageSource.getMessage(messageCode, null, null)).thenReturn(expectedMessage);

        String actualMessage = messageExceptionService.getErrorMessage(messageCode);

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testGetErrorMessage_WithNonExistentCode_ShouldReturnNull() {
        String messageCode = "user.nonexistent.code";

        when(messageSource.getMessage(messageCode, null, null)).thenReturn(null);

        String actualMessage = messageExceptionService.getErrorMessage(messageCode);

        assertNull(actualMessage);
    }
}