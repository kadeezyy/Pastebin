package com.example.pastebin.exceptions.pasteExceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class EmptyFieldsException extends DataIntegrityViolationException {
    public EmptyFieldsException(String message) {
        super(message);
    }
}
