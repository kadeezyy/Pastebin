package com.example.pastebin.dtos;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PasteDto implements Serializable {
    int id;
    String text;
    String hash;
    UserDTO user;

    public String toString() {
        return String.format("""
                        id: %d, text: %s, hash: %s, user: %s
                        """,
                id, text, hash, user);
    }
}
