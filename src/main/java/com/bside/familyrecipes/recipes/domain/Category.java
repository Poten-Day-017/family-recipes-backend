package com.bside.familyrecipes.recipes.domain;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {

    SOUP_NOODLE("탕/면", "CTGR_001"),
    ETC("기타", "CTGR_999"),
    ;
    private final String name;
    private final String value;

    public static Category getByValue(String value) {
        return Arrays.stream(Category.values())
            .filter(category -> category.value.equals(value))
            .findFirst()
            .orElse(Category.ETC);
    }
}
