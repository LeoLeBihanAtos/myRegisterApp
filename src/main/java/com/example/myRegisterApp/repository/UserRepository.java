package com.example.myRegisterApp.repository;

import com.example.myRegisterApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Checks if a username already exists in the database.
     *
     * @param username the username to check
     * @return true if the username exists, otherwise false
     */
    boolean existsByUsername(String username);
}
