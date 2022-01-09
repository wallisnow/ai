package com.ai.sys.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public abstract class RuntimeProblem extends RuntimeException {
    private final String message;
    private final HttpStatus status;
}
