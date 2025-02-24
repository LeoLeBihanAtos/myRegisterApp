package com.example.myRegisterApp.validator;

import com.example.myRegisterApp.annotation.ValidGender;
import com.example.myRegisterApp.enums.Gender;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<ValidGender, String> {

    @Override
    public void initialize(ValidGender constraintAnnotation) {
    }

    @Override
    public boolean isValid(String gender, ConstraintValidatorContext context) {
        if (gender == null) {
            return false;
        }
        try {
            Gender.valueOf(gender.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
