package com.bside.familyrecipes.recipes.dto.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.bside.familyrecipes.recipes.domain.Category;
import com.bside.familyrecipes.recipes.domain.Recipe;
import com.bside.familyrecipes.users.domain.User;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "레시피 등록 요청")
public record RecipeCreateRequest(
    @Schema(description = "레시피 제목", requiredMode = REQUIRED, example = "어머니의 김치찌개")
    String title,
    @Schema(description = "레시피 주인", requiredMode = REQUIRED, example = "어머니")
    String origin,
    @Schema(description = "레시피 소개", requiredMode = NOT_REQUIRED, example = "가족의 레시피를 간단하게 1줄로 소개해보세요.")
    String content,
    @Schema(description = "카테고리 코드", requiredMode = REQUIRED, example = "001")
    String category,
    @Schema(description = "레시피 기준 인원", requiredMode = NOT_REQUIRED, example = "2")
    Integer capacity,
    @Schema(description = "레시피 공개여부", requiredMode = NOT_REQUIRED, example = "Y")
    String totalOpenYn,
    @Schema(description = "필수 재료 리스트", requiredMode = REQUIRED)
    List<Ingredient> ingredientList,
    @Schema(description = "비법 재료 리스트", requiredMode = NOT_REQUIRED)
    List<Ingredient> secretIngredientList,
    @Schema(description = "레시피 조리 순서", requiredMode = REQUIRED)
    List<Procedure> procedureList
) {
    public record Ingredient(
        @Schema(description = "요리 재료 순번", requiredMode = NOT_REQUIRED, example = "1")
        Integer order,
        @Schema(description = "요리 재료 명", requiredMode = REQUIRED, example = "채끝살")
        String name,
        @Schema(description = "요리 재료 량", requiredMode = REQUIRED, example = "300g")
        String amount
    ) {
        public com.bside.familyrecipes.recipes.domain.Ingredient toEntity(String requiredYn) {
            return com.bside.familyrecipes.recipes.domain.Ingredient.builder()
                .orderNo(this.order)
                .name(this.name)
                .amount(this.amount)
                .requiredYn(requiredYn)
                .build();
        }
    }
    public record Procedure(
        @Schema(description = "요리 순서 순번", requiredMode = REQUIRED, example = "1")
        Integer order,
        @Schema(description = "요리 순서 설명", requiredMode = REQUIRED, example = "쌀은 씻어 30분간 불린다. 쪽파는 송송 썰어 둔다.")
        String description
    ) {
        public com.bside.familyrecipes.recipes.domain.Procedure toEntity() {
            return com.bside.familyrecipes.recipes.domain.Procedure.builder()
                .orderNo(this.order)
                .description(this.description)
                .build();
        }
    }

    public Recipe toEntity(User user) {
        var recipe = Recipe.builder()
            .title(this.title)
            .user(user)
            .origin(this.origin)
            .content(this.content)
            .category(Category.getByValue(this.category))
            .capacity(this.capacity)
            .totalOpenYn(this.totalOpenYn)
            .build();

        var ingredientEntityList = new ArrayList<>(Optional.ofNullable(this.ingredientList)
            .orElseGet(Collections::emptyList).stream()
            .map(ingredient -> ingredient.toEntity("Y")).toList());

        Optional.ofNullable(this.secretIngredientList)
            .orElseGet(Collections::emptyList).forEach(ingredient -> {
            var entity = ingredient.toEntity("N");
            ingredientEntityList.add(entity);
        });

        var procedureList = Optional.ofNullable(this.procedureList)
            .orElseGet(Collections::emptyList).stream().map(Procedure::toEntity).toList();

        recipe.setDetailInfo(ingredientEntityList, procedureList);

        return recipe;
    }
}
