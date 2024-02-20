package com.bside.familyrecipes.common.exception;

import org.springframework.http.HttpStatus;

public class AuthException extends ProjectException {
    public AuthException(String message, HttpStatus statusCode) {
        super(message, statusCode);
    }
}
