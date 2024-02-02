package com.bside.familyrecipes.common.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionMessage {
    /** Users **/
    NOT_FOUND_USER("해당하는 유저를 찾을 수 없습니다.");

    private final String message;
}
