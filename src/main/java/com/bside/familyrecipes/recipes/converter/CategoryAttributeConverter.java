package com.bside.familyrecipes.recipes.converter;

import java.util.Optional;

import com.bside.familyrecipes.recipes.domain.Category;

import jakarta.persistence.AttributeConverter;

public class CategoryAttributeConverter implements AttributeConverter<Category, String> {
    @Override
    public String convertToDatabaseColumn(Category attribute) {
        return Optional.ofNullable(attribute).orElse(Category.ETC).getValue();
    }

    @Override
    public Category convertToEntityAttribute(String dbData) {
        return Category.getByValue(dbData);
    }
}
