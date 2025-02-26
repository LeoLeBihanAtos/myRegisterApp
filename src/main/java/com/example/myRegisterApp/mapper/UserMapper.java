package com.example.myRegisterApp.mapper;

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
        return new User(
                null,
                userDTO.getUsername(),
                userDTO.getBirthdate(),
                userDTO.getCountryOfResidence(),
                userDTO.getPhoneNumber(),
                userDTO.getGender()
        );
    }

    /**
     * Converts a User entity to a UserResponseDTO.
     *
     * @param user the user entity
     * @return a UserResponseDTO
     */
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