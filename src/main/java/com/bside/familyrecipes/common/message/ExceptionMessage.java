package com.bside.familyrecipes.common.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionMessage {
    /** auth **/
    INVALID_TOKEN("유효하지 않은 토큰입니다.");

    private final String message;
}
