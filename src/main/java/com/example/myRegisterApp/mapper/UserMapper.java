package com.example.myRegisterApp.mapper;

import com.example.myRegisterApp.enums.Gender;
import com.example.myRegisterApp.model.User;
import com.example.myRegisterApp.model.dto.UserDTO;
import com.example.myRegisterApp.model.dto.UserResponseDTO;

/**
 * Mapper class for converting between User and DTO representations.
 */
public class UserMapper {

    /**
     * Converts a UserDTO to a User entity.
     *
     * @param userDTO the user data transfer object
     * @return a User entity
     */
    public static User toEntity(UserDTO userDTO) {
        return User.builder()
                .username(userDTO.getUsername())
                .birthdate(userDTO.getBirthdate())
                .countryOfResidence(userDTO.getCountryOfResidence())
                .phoneNumber(userDTO.getPhoneNumber())
                .gender(Gender.valueOf(userDTO.getGender().toUpperCase()))
                .build();
    }

    /**
     * Converts a User entity to a UserResponseDTO.
     *
     * @param user the user entity
     * @return a UserResponseDTO
     */
    public static UserResponseDTO toResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .birthdate(user.getBirthdate())
                .countryOfResidence(user.getCountryOfResidence())
                .phoneNumber(user.getPhoneNumber())
                .gender(user.getGender().toString())
                .build();
    }
}