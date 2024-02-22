package com.bside.familyrecipes.error;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorType {

    FILE_UPLOAD_FAIL(BAD_REQUEST, "파일 등록에 실패하였습니다."),
    RECIPE_CREATED_FAIL(BAD_REQUEST, "레시피 등록에 실패하였습니다."),
    RECIPE_NOT_FOUND(NOT_FOUND, "존재하지 않는 레시피입니다."),
    JWT_PAYMENT_REQUIRED(PAYMENT_REQUIRED, "로그인이 필요합니다."),
    INTERNAL_ERROR(INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
