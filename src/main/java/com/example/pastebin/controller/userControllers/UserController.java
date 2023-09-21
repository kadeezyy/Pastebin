package com.example.pastebin.controller.userControllers;

import com.example.pastebin.dtos.UserDTO;
import com.example.pastebin.service.userServices.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/v1/user/add")
    public ResponseEntity<UserDTO> adduserToDatabase(@RequestBody UserDTO userDTO) {
        var resultUser = userService.saveUser(userDTO);
        return ResponseEntity.ok(resultUser);
    }
}
