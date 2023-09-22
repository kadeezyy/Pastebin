package com.example.pastebin.dtos;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class PasteDTO {
    int id;
    String text;
    String hash;
    UserDTO user;
}
