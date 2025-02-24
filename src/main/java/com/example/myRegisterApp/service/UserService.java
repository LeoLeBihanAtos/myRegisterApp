package com.example.myRegisterApp.service;

import com.example.myRegisterApp.mapper.UserMapper;
import com.example.myRegisterApp.model.User;
import com.example.myRegisterApp.model.dto.UserDTO;
import com.example.myRegisterApp.model.dto.UserResponseDTO;
import com.example.myRegisterApp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public UserResponseDTO registerUser(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        validateUser(user);
        return UserMapper.toResponseDTO(userRepository.save(user));
    }



    public Optional<UserResponseDTO> getUserById(Long id) {
        logger.info("Recherche de l'utilisateur avec l'ID : {}", id);
        Optional<User> user = userRepository.findById(id);
        return user.map(UserMapper::toResponseDTO);
    }

    private void validateUser(User user) {
        int age = Period.between(user.getBirthdate(), LocalDate.now()).getYears();
        if (user.getBirthdate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La date de naissance doit être dans le passé");
        }
        if (age < 18) {
            throw new IllegalArgumentException("L'utilisateur doit être majeur (18+ ans)");
        }
        if (!"France".equalsIgnoreCase(user.getCountryOfResidence())) {
            throw new IllegalArgumentException("Seuls les résidents français peuvent s'inscrire");
        }
    }
}