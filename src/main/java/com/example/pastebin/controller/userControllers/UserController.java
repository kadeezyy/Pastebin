package com.example.pastebin.controller.userControllers;

import com.example.pastebin.dtos.UserDTO;
import com.example.pastebin.packet.IResponse;
import com.example.pastebin.packet.MessageResponse;
import com.example.pastebin.service.userServices.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<IResponse> registerUser(@RequestBody UserDTO userDTO) {
        var resultUser = userService.registerUser(userDTO);
        return ResponseEntity.ok(new MessageResponse(resultUser.toString()));
    }

    @PostMapping("/login")
    public ResponseEntity<IResponse> loginUser(@RequestBody UserDTO userDTO) {
        var res = userService.authenticateUser(userDTO);
        return ResponseEntity.ok(new MessageResponse(res.toString()));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        userService.refreshToken(request, response);
    }
}
