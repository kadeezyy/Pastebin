package com.example.pastebin.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Entity
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Table(name = "pastes")
public class Paste {
    @Id
    int id;
    @Column(nullable = false)
    String text;
    @Column(nullable = false, unique = true)
    String hash;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
