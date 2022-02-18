package com.ai.sys.exception;

import org.springframework.http.HttpStatus;

public class ResourceOperationExceptionFactory {

    public static ResourceOperationException createMenuException(String message, HttpStatus status){
        return ResourceOperationException.builder()
                .resourceName("MENU")
                .message(message)
                .status(status)
                .build();
    }

    public static ResourceOperationException createUserException(String message, HttpStatus status){
        return  ResourceOperationException.builder()
                .message(message)
                .status(status)
                .resourceName("USER")
                .build();
    }
}
