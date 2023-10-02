package com.example.pastebin.dtos;

import com.example.pastebin.enums.Roles;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class UserDTO {
    int id;
    String username;
    @Builder.Default
    String password = "hidden";
    Roles role = Roles.Regular;

    public String toString() {
        return String.format("""
                id: %d, username: %s, role: %s""",
                id, username, role);
    }
}
