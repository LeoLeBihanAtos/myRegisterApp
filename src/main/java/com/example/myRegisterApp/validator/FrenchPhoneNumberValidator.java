package com.example.myRegisterApp.validator;

import com.example.myRegisterApp.annotation.FrenchPhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator for French phone numbers.
 * Ensures that the provided phone number follows the format +33XXXXXXXXX.
 */
public class FrenchPhoneNumberValidator implements ConstraintValidator<FrenchPhoneNumber, String> {

    private static final String FRENCH_PHONE_NUMBER_PATTERN = "^\\+33\\d{9}$";

    @Override
    public void initialize(FrenchPhoneNumber constraintAnnotation) {
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if (phoneNumber == null) {
            return false;
        }
        return phoneNumber.matches(FRENCH_PHONE_NUMBER_PATTERN);
    }
}