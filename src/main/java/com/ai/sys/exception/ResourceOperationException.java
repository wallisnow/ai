package com.ai.sys.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResourceOperationException extends RuntimeProblem {
    private final String resourceName;

    @Builder
    public ResourceOperationException(String message, HttpStatus status, String resourceName) {
        super(message, status);
        this.resourceName = resourceName;
    }
}
