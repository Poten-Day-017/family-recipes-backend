package com.bside.familyrecipes.error;

import static com.bside.familyrecipes.error.ErrorType.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bside.familyrecipes.common.dto.response.ErrorDto;
import com.bside.familyrecipes.error.exception.BusinessException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorDto> handleBusinessException(final BusinessException e, final HttpServletRequest request) {
        log.error("[ERROR] RequestURL: {}\n", request.getRequestURL(), e);
        return ResponseEntity
            .status(e.getHttpStatus())
            .body(new ErrorDto(e.getMessage()));
    }
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorDto> handleException(final Exception e, final HttpServletRequest request) {
        log.error("[ERROR] RequestURL: {}\n", request.getRequestURL(), e);
        return ResponseEntity
            .status(INTERNAL_ERROR.getHttpStatus())
            .body(new ErrorDto(INTERNAL_ERROR.getMessage()));
    }
}
