package com.bside.familyrecipes.error.exception;

import com.bside.familyrecipes.error.ErrorType;

public class CreateRecipeFailException extends BusinessException {
    public CreateRecipeFailException() {
        super(ErrorType.RECIPE_CREATED_FAIL);
    }
}
