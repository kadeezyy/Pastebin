package com.example.pastebin.service.userServices;

import com.example.pastebin.dtos.JwtTokenDto;
import com.example.pastebin.dtos.UserDTO;
import com.example.pastebin.entity.JwtToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public interface UserService {
    JwtTokenDto registerUser(UserDTO userDTO);
    JwtTokenDto authenticateUser(UserDTO userDTO);

    UserDTO findUserById(int id);

    List<UserDTO> findAllUsersByUsername(String name);

    void deleteUser(UserDTO userDTO);

    UserDTO updateUser(UserDTO userDTO);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}