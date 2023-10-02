package com.example.pastebin.dtos;

import lombok.*;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class PasteDto {
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
