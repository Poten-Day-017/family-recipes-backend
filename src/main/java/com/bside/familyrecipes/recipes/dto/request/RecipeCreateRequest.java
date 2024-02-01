package com.bside.familyrecipes.recipes.dto.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "레시피 등록 요청")
public record RecipeCreateRequest(
    @Schema(description = "레시피 제목", requiredMode = REQUIRED)
    String title,
    @Schema(description = "레시피 주인", requiredMode = REQUIRED)
    String origin,
    @Schema(description = "레시피 소개", requiredMode = NOT_REQUIRED)
    String content,
    @Schema(description = "카테고리 코드", requiredMode = REQUIRED)
    String category,
    @Schema(description = "레시피 기준 인원", requiredMode = NOT_REQUIRED)
    Integer capacity,
    @Schema(description = "에피소드", requiredMode = NOT_REQUIRED)
    String episode,
    @Schema(description = "에피소드 공개여부", requiredMode = NOT_REQUIRED)
    String episodeOpenYn,
    @Schema(description = "레시피 공개여부", requiredMode = NOT_REQUIRED)
    String totalOpenYn,
    @Schema(description = "요리 재료 리스트", requiredMode = REQUIRED)
    List<Ingredient> ingredientList,
    @Schema(description = "요리 순서 리스트", requiredMode = REQUIRED)
    List<Procedure> procedureList
) {
    public record Ingredient(
        @Schema(description = "요리 재료 순번", requiredMode = REQUIRED)
        Integer order,
        @Schema(description = "요리 재료 명", requiredMode = REQUIRED)
        String name,
        @Schema(description = "요리 재료 량", requiredMode = REQUIRED)
        String amount
    ) {
    }
    public record Procedure(
        @Schema(description = "요리 순서 순번", requiredMode = REQUIRED)
        Integer order,
        @Schema(description = "요리 순서 설명", requiredMode = REQUIRED)
        String description
    ) {
    }
}
