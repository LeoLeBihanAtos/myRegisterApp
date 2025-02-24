package com.example.myRegisterApp.service;

import com.example.myRegisterApp.model.User;
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

    public User registerUser(User user) {
        validateUser(user);
        return userRepository.save(user);
    }



    public Optional<User> getUserById(Long id) {
        logger.info("Recherche de l'utilisateur avec l'ID : {}", id);
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            logger.info("Utilisateur trouvé : {}", user.get().getUsername());
        } else {
            logger.warn("Utilisateur avec l'ID {} non trouvé", id);
        }
        return user;
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