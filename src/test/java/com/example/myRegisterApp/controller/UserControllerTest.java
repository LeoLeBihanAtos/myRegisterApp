package com.example.myRegisterApp.controller;

import com.example.myRegisterApp.model.dto.UserDTO;
import com.example.myRegisterApp.model.dto.UserResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.example.myRegisterApp.service.UserService;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = JsonMapper.builder()
            .findAndAddModules()
            .build();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testRegisterUser_Success() throws Exception {
        UserDTO userDTO = new UserDTO("Alice", LocalDate.of(2000, 1, 1), "France", "+33606060606", "Female");
        UserResponseDTO userResponseDTO = new UserResponseDTO(1L, "Alice", LocalDate.of(2000, 1, 1), "France", "+33606060606", "Female");

        when(userService.registerUser(any(UserDTO.class))).thenReturn(userResponseDTO);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("Alice"));
    }

    @Test
    public void testGetUserById_UserExists() throws Exception {
        UserResponseDTO userResponseDTO = new UserResponseDTO(1L, "Alice", LocalDate.of(2000, 1, 1), "France", "+33606060606", "Female");

        when(userService.getUserById(1L)).thenReturn(Optional.of(userResponseDTO));

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Alice"));
    }

    @Test
    public void testGetUserById_UserNotExists() throws Exception {
        when(userService.getUserById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testRegisterUser_UsernameBlank_ShouldThrowException() throws Exception {
        UserDTO invalidUserDTO = new UserDTO("", LocalDate.of(1990, 1, 1), "France", "33606060606", "Male");

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUserDTO)))
                .andExpect(status().isBadRequest());
    }

}
