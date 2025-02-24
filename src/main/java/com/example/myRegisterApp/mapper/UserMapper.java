package com.example.myRegisterApp.mapper;

import com.example.myRegisterApp.model.User;
import com.example.myRegisterApp.model.dto.UserDTO;
import com.example.myRegisterApp.model.dto.UserResponseDTO;

public class UserMapper {

    public static User toEntity(UserDTO userDTO) {
        return new User(
                null,
                userDTO.getUsername(),
                userDTO.getBirthdate(),
                userDTO.getCountryOfResidence(),
                userDTO.getPhoneNumber(),
                userDTO.getGender()
        );
    }

    public static UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getBirthdate(),
                user.getCountryOfResidence(),
                user.getPhoneNumber(),
                user.getGender()
        );
    }
}