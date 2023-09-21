package com.example.pastebin.entity;

import com.example.pastebin.enums.Roles;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "users")
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(nullable = false)
    String username;
    @Enumerated(EnumType.STRING)
    Roles role;
    @Column(nullable = false)
    String password;

    public User(String username, String password, Roles role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
