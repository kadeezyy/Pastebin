package com.example.pastebin.exceptions.pasteExceptions;

import org.springframework.dao.DataAccessException;

public class AbsencePasteException extends DataAccessException {
    public AbsencePasteException(String message) {
        super(message);
    }
}
