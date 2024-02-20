package com.bside.familyrecipes.common.dto.response;

import org.springframework.http.ResponseEntity;

public record ResponseDto<T>(T data) {
    public static <T> ResponseEntity<T> ok(T data) {
        return ResponseEntity.ok(data);
    }
}
