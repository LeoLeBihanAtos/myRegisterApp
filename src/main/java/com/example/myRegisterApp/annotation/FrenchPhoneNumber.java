package com.example.myRegisterApp.annotation;


import com.example.myRegisterApp.validator.FrenchPhoneNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FrenchPhoneNumberValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FrenchPhoneNumber {
    String message() default "The phone number must be in French format: +33";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}