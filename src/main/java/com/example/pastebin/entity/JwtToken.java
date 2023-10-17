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
    private int id;

    @Column(unique = true)
    private String token;

    private boolean expired;
    private boolean revoked;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TokenType type = TokenType.BEARER;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
