package com.example.pastebin.dtos;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class PasteDto implements Serializable {
    private int id;
    private String text;
    private String hash;
}
