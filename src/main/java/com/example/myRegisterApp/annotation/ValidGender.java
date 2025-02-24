package com.example.myRegisterApp.annotation;

import com.example.myRegisterApp.config.GenderValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = GenderValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidGender {
    String message() default "Invalide gender: male / female / other";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}