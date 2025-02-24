package com.example.myRegisterApp.exception;

import com.example.myRegisterApp.controller.UserController;
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

import java.util.NoSuchElementException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void testHandleIllegalArgumentException_ShouldReturn400() throws Exception {
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "birthdate": "2000-01-01",
                                    "countryOfResidence": "France",
                                    "phoneNumber": "+33606060606",
                                    "gender": "Male"
                                }  \s"""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.username").value("Username is required"));

    }


    @Test
    public void testHandleNoSuchElementException_ShouldReturn404() throws Exception {
        when(userService.getUserById(1L)).thenThrow(new NoSuchElementException("User not found"));

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found"));
    }

    @Test
    public void testHandleGenericException_ShouldReturn500() throws Exception {
        when(userService.getUserById(1L)).thenThrow(new RuntimeException("Server error"));

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Server error"));
    }
}
