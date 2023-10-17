package com.example.pastebin.aop.ControllerAdvicer;

import com.example.pastebin.exceptions.pasteExceptions.EmptyFieldsException;
import com.example.pastebin.exceptions.pasteExceptions.PasteNotFoundException;
import com.example.pastebin.exceptions.userExceptions.UserDoesNotOwnPaste;
import com.example.pastebin.packet.ErrorResponse;
import com.example.pastebin.packet.IResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class PasteControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PasteNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<IResponse> handlePasteNotFoundException(
            PasteNotFoundException exception
    ) {
        System.out.println(exception.getMessage());
        return buildErrorResponse(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyFieldsException.class)
    public ResponseEntity<IResponse> handleEmptyFieldsException(
            EmptyFieldsException exception,
            WebRequest request
    ) {
        System.out.println(exception.getMessage());
        return buildErrorResponse(exception, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UserDoesNotOwnPaste.class)
    public ResponseEntity<IResponse> handleUserDoesNotOwnPasteException(
            UserDoesNotOwnPaste exception,
            WebRequest request
    ) {
        System.out.println(exception.getMessage());
        return buildErrorResponse(exception, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<IResponse> buildErrorResponse(Exception exception, HttpStatus status) {
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage()), status);
    }
}
