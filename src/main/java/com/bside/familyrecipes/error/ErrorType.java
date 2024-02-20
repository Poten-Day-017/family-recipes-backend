package com.bside.familyrecipes.error;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorType {

    RECIPE_NOT_FOUND(NOT_FOUND, "존재하지 않는 레시피입니다."),
    INTERNAL_ERROR(INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
