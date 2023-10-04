package com.example.pastebin.service.userServices;

import com.example.pastebin.aop.aspect.Logged;
import com.example.pastebin.converters.UserConverter;
import com.example.pastebin.dtos.JwtTokenDto;
import com.example.pastebin.dtos.UserDTO;
import com.example.pastebin.entity.JwtToken;
import com.example.pastebin.entity.User;
import com.example.pastebin.enums.TokenType;
import com.example.pastebin.exceptions.userExceptions.NotUniqueUsernameException;
import com.example.pastebin.repositories.JwtTokenRepository;
import com.example.pastebin.repositories.UserRepository;
import com.example.pastebin.service.jwt.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    UserConverter userConverter;
    AuthenticationManager authenticationManager;
    JwtTokenRepository jwtTokenRepository;
    JwtService jwtService;

    @Autowired
    public UserServiceImpl(
            UserRepository userRepository,
            UserConverter userConverter,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            JwtTokenRepository jwtTokenRepository
    ) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.jwtTokenRepository = jwtTokenRepository;
    }

    @Logged
    public JwtTokenDto registerUser(UserDTO userDTO) throws NotUniqueUsernameException{
        User user = User.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(userDTO.getRole()).build();
        if (userRepository.findUserByUsername(user.getUsername()).isPresent())
            throw new NotUniqueUsernameException(user.getUsername());

        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(savedUser);
        var refreshToken = jwtService.generateRefreshToken(savedUser);
        saveUserToken(user, jwtToken);
        return JwtTokenDto.builder()
                .access_token(jwtToken)
                .refresh_token(refreshToken).build();
    }

    @Logged
    public JwtTokenDto authenticateUser(UserDTO userDTO) throws AuthenticationException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDTO.getUsername(),
                        userDTO.getPassword()
                )
        );
        var user = userRepository.findUserByUsername(userDTO.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return JwtTokenDto.builder()
                .access_token(jwtToken)
                .refresh_token(refreshToken)
                .build();
    }

    @Logged
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken, username;
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            return;
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            var user = userRepository.findUserByUsername(username).orElseThrow(
                    () -> new UsernameNotFoundException("No user found")
            );
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authenticationResponse = JwtTokenDto.builder()
                        .access_token(accessToken)
                        .refresh_token(refreshToken).build();
                new ObjectMapper().writeValue(response.getOutputStream(), authenticationResponse);
            }

        }

    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = jwtTokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        jwtTokenRepository.saveAll(validUserTokens);
    }

    @Logged
    public UserDTO findUserById(int id) {
        Optional<User> user = userRepository.findUserById(id);
        return user.map(value -> userConverter.userEntityToDto(value)).orElse(null);
    }

    @Logged
    public List<UserDTO> findAllUsersByUsername(String name) {
        var users = userRepository.findAllUsersByUsername(name);
        if (users.isEmpty())
            throw new UsernameNotFoundException("No users with such username: " + name);
        List<UserDTO> resultUsers = new ArrayList<>();
        for (var user : users)
            user.ifPresent(value -> resultUsers.add(userConverter.userEntityToDto(value)));
        return resultUsers;
    }

    @Logged
    private void saveUserToken(User user, String jwtToken) {
        var token = JwtToken.builder()
                .user(user)
                .token(jwtToken)
                .type(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        jwtTokenRepository.save(token);
    }

    @Override
    public void deleteUser(UserDTO userDTO) {

    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        return null;
    }
}
