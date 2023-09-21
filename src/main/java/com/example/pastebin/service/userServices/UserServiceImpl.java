package com.example.pastebin.service.userServices;

import com.example.pastebin.converters.UserConverter;
import com.example.pastebin.dtos.UserDTO;
import com.example.pastebin.entity.User;
import com.example.pastebin.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    UserConverter userConverter;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userConverter = userConverter;
    }


    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        User user = new User(
                userDTO.getUsername(),
                passwordEncoder.encode(userDTO.getPassword()),
                userDTO.getRole()
        );
        var savedUser = userRepository.save(user);
        return userConverter.userEntityToDto(savedUser);
    }

    @Override
    public UserDTO findUserById(int id) {
        return null;
    }

    @Override
    public List<UserDTO> findUsersByName(String name) {
        return null;
    }

    @Override
    public void deleteUser(UserDTO userDTO) {

    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        return null;
    }
}
