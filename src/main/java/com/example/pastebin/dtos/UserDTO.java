package com.example.pastebin.dtos;

import com.example.pastebin.enums.Roles;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {
    int id;
    String username;
    String password;
    Roles role;
}
