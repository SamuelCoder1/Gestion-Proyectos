package com.riwi.backend.application.exceptions;


import com.riwi.backend.application.dtos.exception.GenericNotFoundExceptions;
import com.riwi.backend.application.dtos.exception.UnauthorizedAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<String> handleUnauthorizedAccessExceptiom(UnauthorizedAccessException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(GenericNotFoundExceptions.class)
    public ResponseEntity<String> handleGenericNotFound(GenericNotFoundExceptions ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
