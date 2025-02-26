package com.example.myRegisterApp.controller;

import com.example.myRegisterApp.model.dto.UserDTO;
import com.example.myRegisterApp.model.dto.UserResponseDTO;
import com.example.myRegisterApp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller for managing user-related operations.
 * Provides endpoints for user registration and retrieval.
 */
@Tag(name = "User Controller", description = "Endpoints for user management")
@RestController
@Validated
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    /**
     * Constructor for UserController.
     *
     * @param userService the service handling user-related operations
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Registers a new user.
     *
     * @param userDTO the user data transfer object containing user details
     * @return ResponseEntity containing the registered user details
     */
    @Operation(summary = "Register a new user", description = "Registers a user based on the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully registered",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserDTO userDTO) {
        logger.info("User registration request for : {}", userDTO.getUsername());
        UserResponseDTO registeredUser = userService.registerUser(userDTO);
        logger.info("User successfully registered : {}", registeredUser.getId());
        return ResponseEntity.ok(registeredUser);
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return ResponseEntity containing the user details if found, otherwise a 404 response
     */
    @Operation(summary = "Get user by ID", description = "Retrieves a user by their unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        logger.info("Request for user details with ID : {}", id);
        return userService.getUserById(id).map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("User with ID {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }
}