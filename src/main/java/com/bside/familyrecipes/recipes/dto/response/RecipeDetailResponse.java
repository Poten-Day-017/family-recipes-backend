package com.bside.familyrecipes.recipes.dto.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import com.bside.familyrecipes.recipes.domain.Ingredient;
import com.bside.familyrecipes.recipes.domain.Procedure;
import com.bside.familyrecipes.recipes.domain.Recipe;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "레시피 상세 정보")
public record RecipeDetailResponse(
    @Schema(description = "레시피 제목", requiredMode = REQUIRED)
    String title,
    @Schema(description = "레시피 주인", requiredMode = REQUIRED)
    String origin,
    @Schema(description = "레시피 소개", requiredMode = NOT_REQUIRED)
    String content,
    @Schema(description = "카테고리 코드", requiredMode = REQUIRED)
    String category,
    @Schema(description = "카테고리 명", requiredMode = REQUIRED)
    String categoryName,
    @Schema(description = "레시피 기준 인원", requiredMode = NOT_REQUIRED)
    Integer capacity,
    @Schema(description = "에피소드", requiredMode = NOT_REQUIRED)
    String episode,
    @Schema(description = "에피소드 공개여부", requiredMode = NOT_REQUIRED)
    String episodeOpenYn,
    @Schema(description = "레시피 공개여부", requiredMode = NOT_REQUIRED)
    String totalOpenYn,
    @Schema(description = "영상 URL", requiredMode = REQUIRED)
    String cookingVideoUrl,
    @Schema(description = "대표 사진 URL", requiredMode = REQUIRED)
    String cookingImageUrl,
    @Schema(description = "요리 필수 재료 리스트", requiredMode = REQUIRED)
    List<IngredientDto> ingredientList,
    @Schema(description = "요리 비법 재료 리스트", requiredMode = REQUIRED)
    List<IngredientDto> secretIngredientList,
    @Schema(description = "요리 순서 리스트", requiredMode = REQUIRED)
    List<ProcedureDto> procedureList


) {
    public record IngredientDto(
        @Schema(description = "요리 재료 순번", requiredMode = REQUIRED)
        Integer order,
        @Schema(description = "요리 재료 명", requiredMode = REQUIRED)
        String name,
        @Schema(description = "요리 재료 량", requiredMode = REQUIRED)
        String amount
    ) {
        public IngredientDto(Ingredient ingredient) {
            this(ingredient.getOrderNo(), ingredient.getName(), ingredient.getAmount());
        }
    }
    public record ProcedureDto(
        @Schema(description = "요리 순서 순번", requiredMode = REQUIRED)
        Integer order,
        @Schema(description = "요리 순서 사진 URL", requiredMode = NOT_REQUIRED)
        String imageUrl,

        @Schema(description = "요리 순서 설명", requiredMode = REQUIRED)
        String description
    ) {
        public ProcedureDto(Procedure procedure) {
            this(procedure.getOrderNo(), procedure.getImageUrl(), procedure.getDescription());
        }
    }

    public RecipeDetailResponse(Recipe recipe) {
        this(recipe.getTitle(), recipe.getOrigin(), recipe.getContent(), recipe.getCategory().getValue(),
            recipe.getCategory()
                .getName(), recipe.getCapacity(), recipe.getEpisode(), recipe.getEpisodeOpenYn(),
            recipe.getTotalOpenYn(), recipe.getCookingVideoUrl(), recipe.getCookingImageUrl(),
            recipe.findIngredientList().stream().map(IngredientDto::new).toList(),
            recipe.findSecretIngredientList().stream().map(IngredientDto::new).toList(),
            recipe.getProcedureList().stream().map(ProcedureDto::new).toList());
    }
}
