package com.bside.familyrecipes.recipes.domain;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {

    KOREAN("한식", "001"),
    CHINESE("중식", "002"),
    WESTERN("양식", "003"),
    JAPANESE("일식", "004"),
    SNACKBAR("분식", "005"),
    DESSERT("디저트", "006"),
    ETC("기타", "999"),
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
