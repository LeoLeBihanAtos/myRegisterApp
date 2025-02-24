package com.example.myRegisterApp.service;

import com.example.myRegisterApp.model.User;
import com.example.myRegisterApp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User validUser;

    @BeforeEach
    void setUp() {
        validUser = new User(null, "Alice", LocalDate.of(2000, 1, 1), "France", "0606060606", "Female");
    }

    @Test
    public void testRegisterUser_WithValidData_ShouldSaveUser() {
        when(userRepository.save(any(User.class))).thenReturn(validUser);

        User registeredUser = userService.registerUser(validUser);

        assertNotNull(registeredUser);
        assertEquals("Alice", registeredUser.getUsername());
    }

    @Test
    public void testRegisterUser_Under18_ShouldThrowException() {
        User minorUser = new User(null, "Bob", LocalDate.now().minusYears(16), "France", "0606060606", "Male");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.registerUser(minorUser));

        assertEquals("L'utilisateur doit être majeur (18+ ans)", exception.getMessage());
    }

    @Test
    public void testRegisterUser_NonFrenchResident_ShouldThrowException() {
        User nonFrenchUser = new User(null, "Charlie", LocalDate.of(1990, 1, 1), "USA", "0606060606", "Male");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.registerUser(nonFrenchUser));

        assertEquals("Seuls les résidents français peuvent s'inscrire", exception.getMessage());
    }


    @Test
    public void testRegisterUser_FutureBirthdate_ShouldThrowException() {
        User invalidUser = new User(null, "Eve", LocalDate.now().plusYears(5), "France", "0606060606", "Female");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.registerUser(invalidUser));

        assertEquals("La date de naissance doit être dans le passé", exception.getMessage());
    }


    @Test
    public void testGetUserById_UserExists_ShouldReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(validUser));

        Optional<User> user = userService.getUserById(1L);

        assertTrue(user.isPresent());
        assertEquals("Alice", user.get().getUsername());
    }

    @Test
    public void testGetUserById_UserNotExists_ShouldReturnEmpty() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<User> user = userService.getUserById(2L);

        assertFalse(user.isPresent());
    }
}
