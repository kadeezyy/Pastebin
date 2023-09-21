package com.example.pastebin.service.userServices;

import com.example.pastebin.dtos.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO saveUser(UserDTO userDTO);

    UserDTO findUserById(int id);

    List<UserDTO> findUsersByName(String name);

    void deleteUser(UserDTO userDTO);

    UserDTO updateUser(UserDTO userDTO);
}