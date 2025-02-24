package com.example.myRegisterApp.controller;

import com.example.myRegisterApp.mapper.UserMapper;
import com.example.myRegisterApp.model.User;
import com.example.myRegisterApp.model.dto.UserDTO;
import com.example.myRegisterApp.model.dto.UserResponseDTO;
import com.example.myRegisterApp.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Validated
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserDTO userDTO) {
        logger.info("Requête pour enregistrer un utilisateur : {}", userDTO.getUsername());
        User user = UserMapper.toEntity(userDTO);
        User registeredUser = userService.registerUser(user);
        UserResponseDTO responseDTO = UserMapper.toResponseDTO(registeredUser);
        logger.info("Utilisateur enregistré avec succès : {}", responseDTO.getId());
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        logger.info("Requête pour obtenir les détails de l'utilisateur avec l'ID : {}", id);
        Optional<User> user = userService.getUserById(id);
        return user.map(value -> {
            UserResponseDTO responseDTO = UserMapper.toResponseDTO(value);
            return ResponseEntity.ok(responseDTO);
        }).orElseGet(() -> {
            logger.warn("Utilisateur avec l'ID {} non trouvé", id);
            return ResponseEntity.notFound().build();
        });
    }
}