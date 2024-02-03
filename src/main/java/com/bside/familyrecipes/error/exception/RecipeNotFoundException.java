package com.bside.familyrecipes.error.exception;

import com.bside.familyrecipes.error.ErrorType;

public class RecipeNotFoundException extends BusinessException {
    public RecipeNotFoundException() {
        super(ErrorType.RECIPE_NOT_FOUND);
    }
}
