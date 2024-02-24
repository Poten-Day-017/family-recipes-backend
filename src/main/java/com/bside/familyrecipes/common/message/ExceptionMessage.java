package com.bside.familyrecipes.common.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionMessage {
    /** Auth **/
    INVALID_TOKEN("유효하지 않은 토큰입니다."),
    INVALID_KAKAO_TOKEN("유효하지 않은 카카오토큰입니다."),

    /** Users **/
    NOT_FOUND_USER("해당하는 유저를 찾을 수 없습니다.");

    private final String message;
}
