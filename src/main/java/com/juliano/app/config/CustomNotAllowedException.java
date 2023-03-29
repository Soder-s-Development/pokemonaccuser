package com.juliano.app.config;

public class CustomNotAllowedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public CustomNotAllowedException(String errorMessage) {
        super(errorMessage);
    }


}
