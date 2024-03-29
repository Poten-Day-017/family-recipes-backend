package com.bside.familyrecipes.error;

import static com.bside.familyrecipes.error.ErrorType.*;

import org.apache.tomcat.util.http.fileupload.impl.InvalidContentTypeException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import com.bside.familyrecipes.common.dto.response.ResponseDto;
import com.bside.familyrecipes.error.exception.BusinessException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ResponseDto<Void>> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e, final HttpServletRequest request) {
        log.error("[ERROR] RequestURL: {}\n", request.getRequestURL(), e);
        return ResponseEntity
            .status(BAD_REQUEST_EXCEPTION.getHttpStatus())
            .body(ResponseDto.error(e.getBindingResult().getAllErrors().get(0).getDefaultMessage()));
    }

    @ExceptionHandler({MultipartException.class, InvalidContentTypeException.class})
    protected ResponseEntity<ResponseDto<Void>> handleInvalidContentTypeException(final Exception e, final HttpServletRequest request) {
        log.error("[ERROR] RequestURL: {}\n", request.getRequestURL(), e);
        return ResponseEntity
            .status(INVALID_CONTENT_TYPE_EXCEPTION.getHttpStatus())
            .body(ResponseDto.error(INVALID_CONTENT_TYPE_EXCEPTION.getMessage()));
    }


    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ResponseDto<Void>> handleBusinessException(final BusinessException e, final HttpServletRequest request) {
        log.error("[ERROR] RequestURL: {}\n", request.getRequestURL(), e);
        return ResponseEntity
            .status(e.getHttpStatus())
            .body(ResponseDto.error(e.getMessage()));
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, HttpRequestMethodNotSupportedException.class})
    protected ResponseEntity<ResponseDto<Void>> handleHttpRequestMethodNotSupportedException(final Exception e,
        final HttpServletRequest request) {
        log.error("[ERROR] RequestURL: {}\n", request.getRequestURL(), e);
        return ResponseEntity
            .status(BAD_REQUEST_EXCEPTION.getHttpStatus())
            .body(ResponseDto.error(BAD_REQUEST_EXCEPTION.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ResponseDto<Void>> handleException(final Exception e, final HttpServletRequest request) {
        log.error("[ERROR] RequestURL: {}\n", request.getRequestURL(), e);
        return ResponseEntity
            .status(INTERNAL_ERROR.getHttpStatus())
            .body(ResponseDto.error(INTERNAL_ERROR.getMessage()));
    }

}
