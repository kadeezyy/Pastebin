package com.example.pastebin.dtos;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
public class PasteDTO {
    int id;
    String text;
    String hash;
    UserDTO user;

    public String toString() {
        return String.format("""
                id: %d, text: %s, hash: %s, user: %s""",
                id, text, hash, user);
    }
}
