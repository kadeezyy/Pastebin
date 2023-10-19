package com.example.pastebin.dtos;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Data
public class SimplePaste implements Serializable {
    private String text;
}