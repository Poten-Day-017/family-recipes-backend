package com.bside.familyrecipes.recipes.facade;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.bside.familyrecipes.error.exception.UserNotFoundException;
import com.bside.familyrecipes.recipes.application.RecipeService;
import com.bside.familyrecipes.recipes.dto.request.RecipeCreateRequest;
import com.bside.familyrecipes.recipes.dto.response.RecipeCategoryResponse;
import com.bside.familyrecipes.recipes.dto.response.RecipeDetailResponse;
import com.bside.familyrecipes.recipes.dto.response.RecipeListResponse;
import com.bside.familyrecipes.storage.application.StorageService;
import com.bside.familyrecipes.users.application.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RecipeFacade {

    private final RecipeService recipeService;
    private final StorageService storageService;
    private final UserRepository userRepository;

    @Transactional
    public Long saveRecipe(Long userId, RecipeCreateRequest recipeCreateRequest,
        Map<String, MultipartFile> multipartFileMap) {

        var user = userRepository.getUserById(userId).orElseThrow(UserNotFoundException::new);

        Map<String, String> storedFiles = storageService.storeFiles(multipartFileMap);

        return recipeService.saveRecipe(user, recipeCreateRequest, storedFiles);
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
