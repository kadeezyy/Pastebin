package com.example.pastebin.service.userServices;

import com.example.pastebin.converters.UserConverter;
import com.example.pastebin.dtos.UserDTO;
import com.example.pastebin.entity.User;
import com.example.pastebin.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    UserConverter userConverter;

    @Autowired
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public UserDTO findUserById(int id) {
        Optional<User> user = userRepository.findUserById(id);
        return user.map(value -> userConverter.userEntityToDto(value)).orElse(null);
    }

    @Override
    public List<UserDTO> findAllUsersByUsername(String name) {
        var users = userRepository.findAllUsersByUsername(name);
        if (users.isEmpty())
            throw new UsernameNotFoundException("No users with such username: " + name);
        List<UserDTO> resultUsers = new ArrayList<>();
        for (var user : users)
            user.ifPresent(value -> resultUsers.add(userConverter.userEntityToDto(value)));
        return resultUsers;
    }

    @Override
    public void deleteUser(UserDTO userDTO) {

    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        return null;
    }
}
