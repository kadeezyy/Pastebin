package com.example.pastebin.exceptions.pasteExceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmptyFieldsException extends DataIntegrityViolationException {
    public EmptyFieldsException(String message) {
        super(message);
    }
}
