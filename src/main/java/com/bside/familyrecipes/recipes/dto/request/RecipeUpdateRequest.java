package com.bside.familyrecipes.recipes.dto.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;
import static java.lang.Boolean.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.bside.familyrecipes.recipes.domain.Category;
import com.bside.familyrecipes.recipes.domain.Ingredient;
import com.bside.familyrecipes.recipes.domain.Procedure;
import com.bside.familyrecipes.recipes.domain.Recipe;
import com.bside.familyrecipes.users.domain.User;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "레시피 수정 요청")
public record RecipeUpdateRequest(
    @NotBlank(message = "레시피 제목을 작성해주세요.")
    @Size(min = 2, max = 30, message = "레시피 제목을 최소 2자 최대 30자 이내로 작성해주세요.")
    @Schema(description = "레시피 제목", requiredMode = REQUIRED, example = "어머니의 김치찌개")
    String title,
    @NotBlank(message = "가족 레시피를 만든 사람을 적어주세요.")
    @Size(max = 30, message = "레시피 주인을 최대 30자 이내로 작성해주세요.")
    @Schema(description = "레시피 주인", requiredMode = REQUIRED, example = "어머니")
    String origin,
    @Schema(description = "레시피 소개", requiredMode = NOT_REQUIRED, example = "가족의 레시피를 간단하게 1줄로 소개해보세요.")
    String content,
    @NotBlank(message = "레시피 카테고리를 선택해주세요.")
    @Schema(description = "카테고리 코드", requiredMode = REQUIRED, example = "001")
    String category,
    @Schema(description = "레시피 기준 인원", requiredMode = NOT_REQUIRED, example = "2")
    Integer capacity,
    @Schema(description = "레시피 공개여부 (삭제예정)", requiredMode = NOT_REQUIRED, example = "Y", hidden = true)
    String totalOpenYn,
    @Schema(description = "레시피 공개여부", requiredMode = NOT_REQUIRED, example = "true")
    Boolean isOpen,
    @Schema(description = "영상 URL (파일 변경 또는 삭제시 빈값)", requiredMode = NOT_REQUIRED, example = "")
    String cookingVideoUrl,
    @Schema(description = "대표 사진 URL (이미지 변경 또는 삭제시 빈값)", requiredMode = NOT_REQUIRED, example = "http://www.daedaesonson.site/static/sample8.jpeg")
    String cookingImageUrl,
    @Schema(description = "요리 필수 재료 리스트", requiredMode = REQUIRED)
    List<IngredientDto> ingredientList,
    @Schema(description = "요리 비법 재료 리스트", requiredMode = NOT_REQUIRED)
    List<IngredientDto> secretIngredientList,
    @Schema(description = "요리 순서 리스트", requiredMode = REQUIRED)
    List<ProcedureDto> procedureList
) {
    public record IngredientDto(
        @Schema(description = "요리 재료 순번", requiredMode = NOT_REQUIRED, example = "1")
        Integer order,
        @Schema(description = "요리 재료 명", requiredMode = REQUIRED, example = "채끝살")
        String name,
        @Schema(description = "요리 재료 량", requiredMode = REQUIRED, example = "300g")
        String amount
    ) {
        public Ingredient toEntity(String requiredYn) {
            return Ingredient.builder()
                .orderNo(this.order)
                .name(this.name)
                .amount(this.amount)
                .requiredYn(requiredYn)
                .build();
        }
    }
    public record ProcedureDto(
        @Schema(description = "요리 순서 순번", requiredMode = REQUIRED, example = "1")
        Integer order,
        @Schema(description = "요리 순서 사진 URL", requiredMode = NOT_REQUIRED, example = "http://www.daedaesonson.site/static/sample6.jpeg")
        String imageUrl,
        @Schema(description = "요리 순서 설명", requiredMode = REQUIRED, example = "쌀은 씻어 30분간 불린다. 쪽파는 송송 썰어 둔다.")
        String description
    ) {
        public Procedure toEntity() {
            return Procedure.builder()
                .orderNo(this.order)
                .description(this.description)
                .imageUrl(this.imageUrl)
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
            .totalOpenYn(TRUE.equals(isOpen) ? "Y" : "N")
            .cookingImageUrl(this.cookingImageUrl)
            .cookingVideoUrl(this.cookingVideoUrl)
            .build();

        var ingredientEntityList = new ArrayList<>(Optional.ofNullable(this.ingredientList)
            .orElseGet(Collections::emptyList).stream()
            .map(ingredientDto -> ingredientDto.toEntity("Y")).toList());

        Optional.ofNullable(this.secretIngredientList)
            .orElseGet(Collections::emptyList).forEach(ingredientDto -> {
            var entity = ingredientDto.toEntity("N");
            ingredientEntityList.add(entity);
        });

        var procedureList = Optional.ofNullable(this.procedureList)
            .orElseGet(Collections::emptyList).stream().map(ProcedureDto::toEntity).toList();

        recipe.setDetailInfo(ingredientEntityList, procedureList);

        return recipe;
    }
}
