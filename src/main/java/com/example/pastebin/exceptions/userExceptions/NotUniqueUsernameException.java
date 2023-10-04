package com.example.pastebin.exceptions.userExceptions;

public class NotUniqueUsernameException extends RuntimeException{
    public NotUniqueUsernameException(String username) {
        super("Username is not unique: " + username);
    }
}
