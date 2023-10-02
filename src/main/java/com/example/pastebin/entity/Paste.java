package com.example.pastebin.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serial;

@Entity
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Table(name = "pastes")
@AllArgsConstructor
@Builder
public class Paste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false)
    String text;

    @Column(nullable = false, unique = true)
    String hash;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    public Paste(String text, String hash) {
        this.text = text;
        this.hash = hash;
    }
}
