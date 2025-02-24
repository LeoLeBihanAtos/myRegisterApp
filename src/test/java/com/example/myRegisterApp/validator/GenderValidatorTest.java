package com.example.myRegisterApp.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GenderValidatorTest {

    private GenderValidator genderValidator;

    @BeforeEach
    public void setUp() {
        genderValidator = new GenderValidator();
    }

    @Test
    public void testValidGenderMale() {
        assertTrue(genderValidator.isValid("Male", null));
    }

    @Test
    public void testValidGenderFemale() {
        assertTrue(genderValidator.isValid("Female", null));
    }

    @Test
    public void testValidGenderOther() {
        assertTrue(genderValidator.isValid("Other", null));
    }

    @Test
    public void testInvalidGender() {
        assertFalse(genderValidator.isValid("InvalidGender", null));
    }

    @Test
    public void testNullGender() {
        assertFalse(genderValidator.isValid(null, null));
    }

    @Test
    public void testEmptyGender() {
        assertFalse(genderValidator.isValid("", null));
    }
}
