package com.example.pastebin.dtos;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class PasteDTO {
    String text;
    String hash;
}
