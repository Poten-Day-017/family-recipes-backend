package com.bside.familyrecipes.error.exception;

import com.bside.familyrecipes.error.ErrorType;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException() {
        super(ErrorType.RECIPE_NOT_FOUND);
    }
}
