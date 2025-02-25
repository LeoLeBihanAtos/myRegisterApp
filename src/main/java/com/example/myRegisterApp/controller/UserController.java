package com.example.myRegisterApp.controller;

import com.example.myRegisterApp.model.dto.UserDTO;
import com.example.myRegisterApp.model.dto.UserResponseDTO;
import com.example.myRegisterApp.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@Validated
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserDTO userDTO) {
        logger.info("User registration request for : {}", userDTO.getUsername());
        UserResponseDTO registeredUser = userService.registerUser(userDTO);
        logger.info("User successfully registered : {}", registeredUser.getId());
        return ResponseEntity.ok(registeredUser);
    }

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