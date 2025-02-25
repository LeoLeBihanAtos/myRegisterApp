package com.example.myRegisterApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_user", indexes = {
        @Index(name = "idx_username", columnList = "username")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private LocalDate birthdate;
    private String countryOfResidence;
    private String phoneNumber;
    private String gender;
}
