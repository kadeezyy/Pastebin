package com.example.pastebin.exceptions.pasteExceptions;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PasteNotFoundException extends DataAccessException {
    public PasteNotFoundException(int id) {
        super("There is no paste with provided id: " + id);
    }
}
