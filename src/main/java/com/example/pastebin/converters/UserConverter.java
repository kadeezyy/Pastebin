package com.example.pastebin.converters;

import com.example.pastebin.dtos.UserDTO;
import com.example.pastebin.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public UserDTO userEntityToDto(User user) {
        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setRole(user.getRole());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
}
