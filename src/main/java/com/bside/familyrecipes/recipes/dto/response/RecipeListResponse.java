package com.bside.familyrecipes.recipes.dto.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import org.springframework.data.domain.Page;

import com.bside.familyrecipes.recipes.domain.Recipe;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "레시피 목록")
public record RecipeListResponse(
    @Schema(description = "레시피 목록", requiredMode = REQUIRED)
    List<RecipeList> recipeList,
    @Schema(description = "현재 페이지", requiredMode = REQUIRED)
    int page,
    @Schema(description = "페이지 당 데이터 개수", requiredMode = REQUIRED)
    int size,
    @Schema(description = "다음 페이지 여부", requiredMode = REQUIRED)
    boolean hasNext
) {

    public record RecipeList(
        @Schema(description = "레시피 순번", requiredMode = REQUIRED)
        Integer order,
        @Schema(description = "레시피 아이디", requiredMode = REQUIRED)
        Long recipeId,
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
        @Schema(description = "레시피 공개여부 (삭제예정)", requiredMode = REQUIRED, hidden = true)
        String totalOpenYn,
        @Schema(description = "레시피 공개여부", requiredMode = REQUIRED)
        Boolean isOpen,
        @Schema(description = "요리 대표 사진 URL", requiredMode = REQUIRED)
        String cookingImageUrl,
        @Schema(description = "레시피 등록일", requiredMode = REQUIRED)
        String createdAt
    ) {
        public RecipeList(Recipe recipe) {
            this(recipe.getOrderNo(), recipe.getId(), recipe.getTitle(), recipe.getOrigin(), recipe.getContent(), recipe.getCategory().getValue(),
                recipe.getCategory().getName(),
                recipe.getCapacity(), recipe.getTotalOpenYn(), "Y".equals(recipe.getTotalOpenYn()), recipe.getCookingImageUrl(), recipe.getFormattedCreatedAt());
        }
    }

    public RecipeListResponse(Page<Recipe> recipeSlice) {
        this(recipeSlice.getContent()
                .stream()
                .map(RecipeList::new)
                .toList(),
            recipeSlice.getNumber() + 1,
            recipeSlice.getNumberOfElements(),
            recipeSlice.hasNext());
    }
}
