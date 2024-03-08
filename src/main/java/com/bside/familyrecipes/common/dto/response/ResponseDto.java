package com.bside.familyrecipes.common.dto.response;

import org.springframework.http.ResponseEntity;

public record ResponseDto<T>(
    String result,
    String message,
    T data) {
    public static <T> ResponseEntity<T> ok(T data) {
        return ResponseEntity.ok(data);
    }

    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>("success", null, data);
    }
    public static ResponseDto<Void> error(String message) {
        return new ResponseDto<>("fail", message, null);
    }
}
