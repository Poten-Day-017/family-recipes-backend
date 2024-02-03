package com.bside.familyrecipes.recipes.facade;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.bside.familyrecipes.recipes.application.RecipeService;
import com.bside.familyrecipes.recipes.dto.request.RecipeCreateRequest;
import com.bside.familyrecipes.recipes.dto.response.RecipeCategoryResponse;
import com.bside.familyrecipes.recipes.dto.response.RecipeDetailResponse;
import com.bside.familyrecipes.recipes.dto.response.RecipeListResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RecipeFacade {

    private final RecipeService recipeService;

    @Transactional
    public Long saveRecipe(Long userId, RecipeCreateRequest recipeCreateRequest,
        Map<String, MultipartFile> multipartFileMap) {
        return null;
    }

    public RecipeListResponse findRecipeList(Long userId, Pageable pageable) {
        var recipeSlice = recipeService.findRecipeList(userId, pageable);
        return new RecipeListResponse(recipeSlice);
    }

    public RecipeDetailResponse getRecipeDetail(Long userId, Long recipeId) {
        var recipe = recipeService.findRecipeById(userId, recipeId);
        return new RecipeDetailResponse(recipe);
    }

    public RecipeCategoryResponse findCategoryList() {
        var categoryList = recipeService.findCategoryList();
        return RecipeCategoryResponse.from(categoryList);
    }
}
