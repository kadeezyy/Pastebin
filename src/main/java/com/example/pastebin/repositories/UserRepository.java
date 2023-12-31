package com.example.pastebin.repositories;

import com.example.pastebin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserById(int id);
    Optional<User> findUserByUsername(String username);

    @Query("SELECT u from User u where u.username = ?1")
    List<Optional<User>> findAllUsersByUsername(String username);
}
