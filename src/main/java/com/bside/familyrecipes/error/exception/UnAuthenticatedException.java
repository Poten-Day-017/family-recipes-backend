package com.bside.familyrecipes.error.exception;

import com.bside.familyrecipes.error.ErrorType;

public class UnAuthenticatedException extends BusinessException {
    public UnAuthenticatedException() {
        super(ErrorType.UNAUTHORIZED_EXCEPTION);
    }
}
