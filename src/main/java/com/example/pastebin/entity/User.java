package com.example.pastebin.entity;

import com.example.pastebin.enums.Roles;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false)
    String username;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    Roles role = Roles.Regular;

    @Column(nullable = false)
    String password;

    public User(String username, String password, Roles role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
