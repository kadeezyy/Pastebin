package com.example.pastebin.entity;

import com.example.pastebin.enums.Roles;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String username;
    @Enumerated(EnumType.STRING)
    Roles role;
    String password;
}
