package com.example.myRegisterApp.model.dto;

import com.example.myRegisterApp.annotation.FrenchPhoneNumber;
import com.example.myRegisterApp.annotation.ValidGender;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object for user input validation.
 */
@Schema(description = "User Data Transfer Object")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @Schema(description = "User's username", example = "jamespotter")
    @NotBlank(message = "Username is required")
    @Size(min = 2, max = 20, message = "Username must be between 2 and 20 characters")
    private String username;

    @Schema(description = "User's birthdate", example = "1990-05-15")
    @NotNull(message = "Birthdate is required")
    private LocalDate birthdate;

    @Schema(description = "User's country of residence", example = "France")
    @NotBlank(message = "Country of residence is required")
    private String countryOfResidence;

    @Schema(description = "User's phone number (french format)", example = "+33612345678")
    @FrenchPhoneNumber
    private String phoneNumber;

    @Schema(description = "User's gender", example = "MALE")
    @ValidGender
    private String gender;
}