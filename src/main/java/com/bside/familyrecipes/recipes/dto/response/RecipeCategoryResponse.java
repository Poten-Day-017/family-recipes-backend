package com.bside.familyrecipes.recipes.dto.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import com.bside.familyrecipes.recipes.domain.Category;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "레시피 카테고리 목록")
public record RecipeCategoryResponse(
    @Schema(description = "레시피 카테고리 목록")
    List<CategoryDto> categoryList
) {

    public record CategoryDto(
        @Schema(description = "카테고리 명", requiredMode = REQUIRED)
        String name,
        @Schema(description = "카테고리 코드", requiredMode = REQUIRED)
        String code
    ) {
    }

    public static RecipeCategoryResponse from(List<Category> categoryList) {
        return new RecipeCategoryResponse(categoryList.stream()
            .map(category -> new CategoryDto(category.getName(), category.getValue()))
            .toList());
    }
}
