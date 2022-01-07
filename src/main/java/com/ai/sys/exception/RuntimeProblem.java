package com.ai.sys.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public abstract class RuntimeProblem extends RuntimeException {
    private final String message;
    private final HttpStatus status;
}
