package com.example.pastebin.repositories;

import com.example.pastebin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserById(int id);
    List<User> findUsersByUsername(String username);
}
