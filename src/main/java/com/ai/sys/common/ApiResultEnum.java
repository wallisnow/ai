package com.ai.sys.common;

public enum ApiResultEnum {
    SUCCESS("200", "ok"),
    FAILED("400", "Request fielded"),
    ERROR("500", "Server busy"),
    TOKEN_USER_INVALID("70000", "Token expired or no login"),
    ;

    private final String message;
    private final String status;

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    ApiResultEnum(String status, String message) {
        this.message = message;
        this.status = status;
    }
}
