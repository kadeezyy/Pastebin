package com.example.pastebin.dtos;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class PasteDto implements Serializable {
    private int id;
    private String text;
    private String hash;
}
