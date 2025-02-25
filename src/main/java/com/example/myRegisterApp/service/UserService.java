package com.example.myRegisterApp.service;

import com.example.myRegisterApp.exception.BirthdateInFutureException;
import com.example.myRegisterApp.exception.NonFrenchResidentException;
import com.example.myRegisterApp.exception.UnderageUserException;
import com.example.myRegisterApp.exception.UsernameAlreadyExistsException;
import com.example.myRegisterApp.mapper.UserMapper;
import com.example.myRegisterApp.model.User;
import com.example.myRegisterApp.model.dto.UserDTO;
import com.example.myRegisterApp.model.dto.UserResponseDTO;
import com.example.myRegisterApp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    private final UserRepository userRepository;
    private final MessageExceptionService messageExceptionService;

    public UserService(UserRepository userRepository, MessageExceptionService messageExceptionService) {
        this.userRepository = userRepository;
        this.messageExceptionService = messageExceptionService;
    }

    public UserResponseDTO registerUser(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        validateUser(user);
        return UserMapper.toResponseDTO(userRepository.save(user));
    }



    public Optional<UserResponseDTO> getUserById(Long id) {
        logger.info("User ID search : {}", id);
        Optional<User> user = userRepository.findById(id);
        return user.map(UserMapper::toResponseDTO);
    }

    private void validateUser(User user) {
        int age = Period.between(user.getBirthdate(), LocalDate.now()).getYears();
        if (user.getBirthdate().isAfter(LocalDate.now())) {
            throw new BirthdateInFutureException(this.messageExceptionService.getErrorMessage("birthdate.past"));
        }
        if (age < 18) {
            throw new UnderageUserException(this.messageExceptionService.getErrorMessage("user.age.underage"));
        }
        if (!"France".equalsIgnoreCase(user.getCountryOfResidence())) {
            throw new NonFrenchResidentException(this.messageExceptionService.getErrorMessage("user.residence.onlyfrench"));
        }
        if (userRepository.existsByUsername(user.getUsername())) {
                throw new UsernameAlreadyExistsException(this.messageExceptionService.getErrorMessage("user.username.existing"));
        }
    }
}