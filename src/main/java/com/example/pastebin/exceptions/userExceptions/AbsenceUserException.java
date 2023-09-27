package com.example.pastebin.exceptions.userExceptions;

import com.example.pastebin.exceptions.pasteExceptions.AbsencePasteException;

public class AbsenceUserException extends RuntimeException{
    public AbsenceUserException(String message) {
        super(message);
    }
}
