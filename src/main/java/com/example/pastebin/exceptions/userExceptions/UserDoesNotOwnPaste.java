package com.example.pastebin.exceptions.userExceptions;

public class UserDoesNotOwnPaste extends RuntimeException{
    public UserDoesNotOwnPaste() {
        super("This user does not own provided paste");
    }
}
