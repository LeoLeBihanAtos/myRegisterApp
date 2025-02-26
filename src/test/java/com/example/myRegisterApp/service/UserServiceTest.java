package com.example.myRegisterApp.service;

import com.example.myRegisterApp.enums.Gender;
import com.example.myRegisterApp.exception.BirthdateInFutureException;
import com.example.myRegisterApp.exception.NonFrenchResidentException;
import com.example.myRegisterApp.exception.UnderageUserException;
import com.example.myRegisterApp.exception.UsernameAlreadyExistsException;
import com.example.myRegisterApp.model.User;
import com.example.myRegisterApp.model.dto.UserDTO;
import com.example.myRegisterApp.model.dto.UserResponseDTO;
import com.example.myRegisterApp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private MessageExceptionService messageExceptionService;

    private UserDTO validUserDTO;
    private User validUser;


    private static final String BIRTHDATE_PAST_ERROR_MESSAGE = "The birthdate must be in the past";
    private static final String UNDERAGE_ERROR_MESSAGE = "User must be of legal age (18+ years)";
    private static final String RESIDENCE_ONLY_FRENCH_ERROR_MESSAGE = "Only French residents can register";
    private static final String USERNAME_EXISTING_ERROR_MESSAGE = "This username already exists";




    @BeforeEach
    void setUp() {
        validUserDTO = new UserDTO("Alice", LocalDate.of(2000, 1, 1), "France", "0606060606", "Female");
        validUser = new User(null, "Alice", LocalDate.of(2000, 1, 1), "France", "0606060606", Gender.FEMALE);

    }

    @Test
    public void testRegisterUser_WithValidData_ShouldSaveUser() {
        when(userRepository.save(any(User.class))).thenReturn(validUser);
        UserResponseDTO userResponseDTO = userService.registerUser(validUserDTO);

        assertNotNull(userResponseDTO);
        assertEquals("Alice", userResponseDTO.getUsername());
    }

    @Test
    public void testRegisterUser_Under18_ShouldThrowException() {
        UserDTO minorUserDTO = new UserDTO("Bob", LocalDate.now().minusYears(16), "France", "0606060606", "Male");

        when(messageExceptionService.getErrorMessage("user.age.underage")).thenReturn(UNDERAGE_ERROR_MESSAGE);

        UnderageUserException exception = assertThrows(UnderageUserException.class, () -> userService.registerUser(minorUserDTO));
        assertEquals(UNDERAGE_ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    public void testRegisterUser_NonFrenchResident_ShouldThrowException() {
        UserDTO nonFrenchUserDTO = new UserDTO("Eve", LocalDate.now().minusYears(20), "Italie", "0606060606", "Female");

        when(messageExceptionService.getErrorMessage("user.residence.onlyfrench")).thenReturn(RESIDENCE_ONLY_FRENCH_ERROR_MESSAGE);

        NonFrenchResidentException exception = assertThrows(NonFrenchResidentException.class, () -> userService.registerUser(nonFrenchUserDTO));
        assertEquals(RESIDENCE_ONLY_FRENCH_ERROR_MESSAGE, exception.getMessage());
    }


    @Test
    public void testRegisterUser_FutureBirthdate_ShouldThrowException() {
        UserDTO invalidUserDTO = new UserDTO("Eve", LocalDate.now().plusYears(5), "France", "0606060606", "Female");

        when(messageExceptionService.getErrorMessage("birthdate.past")).thenReturn(BIRTHDATE_PAST_ERROR_MESSAGE);

        BirthdateInFutureException exception = assertThrows(BirthdateInFutureException.class, () -> userService.registerUser(invalidUserDTO));
        assertEquals(BIRTHDATE_PAST_ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    public void testRegisterUser_UsernameAlreadyExist_ShouldThrowException() {
        UserDTO invalidUserDTO = new UserDTO("Eve", LocalDate.now().minusYears(20), "France", "0606060606", "Female");

        when(userRepository.existsByUsername(invalidUserDTO.getUsername())).thenReturn(true);
        when(messageExceptionService.getErrorMessage("user.username.existing")).thenReturn(USERNAME_EXISTING_ERROR_MESSAGE);


        UsernameAlreadyExistsException exception = assertThrows(UsernameAlreadyExistsException.class, () -> userService.registerUser(invalidUserDTO));
        assertEquals(USERNAME_EXISTING_ERROR_MESSAGE, exception.getMessage());
    }


    @Test
    public void testGetUserById_UserExists_ShouldReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(validUser));

        Optional<UserResponseDTO> userResponseDTO = userService.getUserById(1L);

        assertTrue(userResponseDTO.isPresent());
        assertEquals("Alice", userResponseDTO.get().getUsername());
    }

    @Test
    public void testGetUserById_UserNotExists_ShouldReturnEmpty() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<UserResponseDTO> userResponseDTO = userService.getUserById(2L);

        assertFalse(userResponseDTO.isPresent());
    }
}
