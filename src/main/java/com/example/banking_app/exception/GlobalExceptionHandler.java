package com.example.banking_app.exception;

import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<?> handleAccountNotFound(AccountNotFoundException message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", message.getMessage()));
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<?> handleInsufficientFunds(InsufficientFundsException message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", message.getMessage()));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handleGenericException(InvalidCredentialsException message){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", message.getMessage()));
    }


}
