package com.bside.familyrecipes.error.exception;

import org.springframework.http.HttpStatus;

import com.bside.familyrecipes.error.ErrorType;

public class BusinessException extends RuntimeException {

    private final ErrorType errorType;

    public BusinessException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public HttpStatus getHttpStatus() {
        return errorType.getHttpStatus();
    }

    @Override
    public String getMessage() {
        return errorType.getMessage();
    }
}
