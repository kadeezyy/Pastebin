package com.example.pastebin.aop.ControllerAdvicer;

import com.example.pastebin.exceptions.userExceptions.NotUniqueUsernameException;
import com.example.pastebin.packet.ErrorResponse;
import com.example.pastebin.packet.IResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UserControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotUniqueUsernameException.class)
    public ResponseEntity<IResponse> handleNotUniqueUsernameException(
            NotUniqueUsernameException exception
    ) {
        return buildErrorResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<IResponse> handleUsernameNotFoundException (
            UsernameNotFoundException exception
    ) {
        System.out.println(exception.getMessage());
        return buildErrorResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<IResponse> handleAuthenticationException (
            AuthenticationException exception
    ) {
        System.out.println(exception.getMessage());

        return buildErrorResponse(exception, HttpStatus.BAD_REQUEST);
    }


    private ResponseEntity<IResponse> buildErrorResponse(Exception exception, HttpStatus status) {
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage()), status);
    }
}
