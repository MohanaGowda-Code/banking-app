package com.example.banking_app.exception;

public class InvalidCredentialsException  extends RuntimeException{

    public InvalidCredentialsException( String message){
        super(message);
    }
}
