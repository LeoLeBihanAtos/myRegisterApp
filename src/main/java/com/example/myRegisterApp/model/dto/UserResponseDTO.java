package com.example.myRegisterApp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String username;
    private LocalDate birthdate;
    private String countryOfResidence;
    private String phoneNumber;
    private String gender;
}