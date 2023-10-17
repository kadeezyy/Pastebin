package com.example.pastebin.dtos;

import com.example.pastebin.enums.Roles;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class UserDTO implements Serializable {
    private int id;
    private String username;

    @Builder.Default
    private String password = "hidden";

    private Roles role = Roles.Regular;

    public String toString() {
        return String.format("""
                        id: %d, username: %s, role: %s
                        """,
                id, username, role);
    }
}
