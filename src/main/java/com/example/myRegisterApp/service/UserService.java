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

/**
 * Service for handling user-related operations.
 * Manages user registration and retrieval.
 */
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    private final UserRepository userRepository;
    private final MessageExceptionService messageExceptionService;

    /**
     * Constructor for UserService.
     *
     * @param userRepository the repository handling user persistence
     * @param messageExceptionService the service handling exception messages
     */
    public UserService(UserRepository userRepository, MessageExceptionService messageExceptionService) {
        this.userRepository = userRepository;
        this.messageExceptionService = messageExceptionService;
    }

    /**
     * Registers a new user after validation.
     *
     * @param userDTO the user data transfer object containing user details
     * @return UserResponseDTO containing registered user details
     */
    public UserResponseDTO registerUser(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        validateUser(user);
        return UserMapper.toResponseDTO(userRepository.save(user));
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user
     * @return an Optional containing the UserResponseDTO if found, otherwise empty
     */
    public Optional<UserResponseDTO> getUserById(Long id) {
        logger.info("User ID search : {}", id);
        Optional<User> user = userRepository.findById(id);
        return user.map(UserMapper::toResponseDTO);
    }

    /**
     * Validates a user entity based on predefined business rules.
     *
     * @param user the user entity to validate
     * @throws BirthdateInFutureException if the birthdate is in the future
     * @throws UnderageUserException if the user is under 18 years old
     * @throws NonFrenchResidentException if the user is not a resident of France
     * @throws UsernameAlreadyExistsException if the username already exists
     */
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