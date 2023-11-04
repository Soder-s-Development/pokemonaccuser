package com.juliano.app.exceptions;

public class CustomNotFoundException extends ClassNotFoundException{

    public CustomNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
