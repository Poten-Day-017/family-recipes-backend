package com.bside.familyrecipes.error;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;
    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    @DisplayName("@Valid 메시지 리턴시 @Size를 제일 나중에 리턴한다.")
    void handleMethodArgumentNotValidExceptionTest() {
        //given
        var exception = mock(MethodArgumentNotValidException.class);
        when(exception.getAllErrors()).thenReturn(
            List.of(
                new ObjectError("test1", new String[] {"Size"}, new String[] {}, "test1"),
                new ObjectError("test2", new String[] {"Size"}, new String[] {}, "test2"),
                new ObjectError("test3", new String[] {"Empty"}, new String[] {}, "test3")
            )
        );
        var request = new MockHttpServletRequest();

        //when
        var response = exceptionHandler.handleMethodArgumentNotValidException(exception, request);

        //then
        assertNotNull(response.getBody());
        log.info("response >> {}", response.getBody());
        assertEquals("fail", response.getBody().result());
        assertEquals("test3", response.getBody().message());
    }

}