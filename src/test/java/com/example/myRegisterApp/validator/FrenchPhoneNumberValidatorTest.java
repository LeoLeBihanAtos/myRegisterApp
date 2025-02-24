package com.example.myRegisterApp.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class FrenchPhoneNumberValidatorTest {

    private FrenchPhoneNumberValidator phoneNumberValidator;

    @BeforeEach
    public void setUp() {
        phoneNumberValidator = new FrenchPhoneNumberValidator();
    }

    @Test
    public void testValidPhoneNumber() {
        assertTrue(phoneNumberValidator.isValid("+33123456789", null));
    }

    @Test
    public void testInvalidPhoneNumber() {
        assertFalse(phoneNumberValidator.isValid("1234567890", null));
    }

    @Test
    public void testNullPhoneNumber() {
        assertFalse(phoneNumberValidator.isValid(null, null));
    }

    @Test
    public void testEmptyPhoneNumber() {
        assertFalse(phoneNumberValidator.isValid("", null));
    }
}