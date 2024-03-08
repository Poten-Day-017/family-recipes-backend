package com.bside.familyrecipes.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ProjectException extends RuntimeException{
    private final HttpStatus statusCode;

    public ProjectException(String message, HttpStatus statusCode){
        super(message);
        this.statusCode = statusCode;
    }
}
