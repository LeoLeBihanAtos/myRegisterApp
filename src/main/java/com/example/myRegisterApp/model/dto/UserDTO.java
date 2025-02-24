package com.example.myRegisterApp.model.dto;

import com.example.myRegisterApp.annotation.FrenchPhoneNumber;
import com.example.myRegisterApp.annotation.ValidGender;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotBlank(message = "Username is required")
    @Size(min = 2, max = 20, message = "Username must be between 2 and 20 characters")
    private String username;

    @Past(message = "Birthdate must be in the past")
    @NotNull(message = "Birthdate is required")
    private LocalDate birthdate;

    @NotBlank(message = "Country of residence is required")
    private String countryOfResidence;

    @FrenchPhoneNumber
    private String phoneNumber;

    @ValidGender
    private String gender;
}