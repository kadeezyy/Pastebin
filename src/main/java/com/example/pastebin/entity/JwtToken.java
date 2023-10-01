package com.example.pastebin.entity;

import com.example.pastebin.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class JwtToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(unique = true)
    String token;

    boolean expired;
    boolean revoked;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    TokenType type = TokenType.BEARER;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
