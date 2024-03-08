package com.bside.familyrecipes.recipes.facade;

import java.util.Map;
import java.util.Objects;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.bside.familyrecipes.error.exception.UnAuthenticatedException;
import com.bside.familyrecipes.error.exception.UserNotFoundException;
import com.bside.familyrecipes.recipes.application.RecipeService;
import com.bside.familyrecipes.recipes.dto.request.RecipeCreateRequest;
import com.bside.familyrecipes.recipes.dto.request.RecipeUpdateRequest;
import com.bside.familyrecipes.recipes.dto.response.RecipeCategoryResponse;
import com.bside.familyrecipes.recipes.dto.response.RecipeDetailResponse;
import com.bside.familyrecipes.recipes.dto.response.RecipeListResponse;
import com.bside.familyrecipes.recipes.application.StorageService;
import com.bside.familyrecipes.users.application.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecipeFacade {

    private final RecipeService recipeService;
    private final StorageService storageService;
    private final UserRepository userRepository;

    @Transactional
    public Long createRecipe(Long userId, RecipeCreateRequest recipeCreateRequest,
        Map<String, MultipartFile> multipartFileMap) {

        var user = userRepository.getUserById(userId).orElseThrow(UserNotFoundException::new);
        var recipe = recipeCreateRequest.toEntity(user);

        Map<String, String> storedFiles = storageService.storeFiles(multipartFileMap);

        return recipeService.saveRecipe(user, recipe, storedFiles);
    }

    public RecipeListResponse findRecipeList(Long userId, Pageable pageable) {
        var recipeSlice = recipeService.findRecipeList(userId, pageable);
        return new RecipeListResponse(recipeSlice);
    }

    public RecipeDetailResponse getRecipeDetail(Long userId, Long recipeId) {
        var recipe = recipeService.findRecipeById(recipeId);

        if (!"Y".equals(recipe.getTotalOpenYn())
            && !Objects.equals(recipe.getUser().getId(), userId)) {
            throw new UnAuthenticatedException();
        }

        return new RecipeDetailResponse(recipe);
    }

    public RecipeCategoryResponse findCategoryList() {
        var categoryList = recipeService.findCategoryList();
        return RecipeCategoryResponse.from(categoryList);
    }

    @Transactional
    public Long updateRecipe(long userId, Long recipeId, RecipeUpdateRequest recipeUpdateRequest,
        Map<String, MultipartFile> multipartFileMap) {

        var user = userRepository.getUserById(userId).orElseThrow(UserNotFoundException::new);
        var recipe = recipeService.findRecipeById(recipeId);

        if (!Objects.equals(user.getId(), recipe.getUser().getId())) {
            throw new UnAuthenticatedException();
        }

        var requestEntity = recipeUpdateRequest.toEntity(user);

        recipe.update(requestEntity);

        Map<String, String> storedFiles = storageService.storeFiles(multipartFileMap);

        return recipeService.saveRecipe(user, recipe, storedFiles);
    }

    @Transactional
    public void deleteRecipe(long userId, Long recipeId) {
        var user = userRepository.getUserById(userId).orElseThrow(UserNotFoundException::new);
        var recipe = recipeService.findRecipeById(recipeId);

        if (!Objects.equals(user.getId(), recipe.getUser().getId())) {
            throw new UnAuthenticatedException();
        }

        var recipes = recipeService.findRecipeByUserAndOrderNoGreaterThan(user, recipe.getOrderNo());
        recipes.forEach(recipe1 -> recipe1.updateOrder(recipe1.getOrderNo() - 1));
        recipeService.saveAll(recipes);
        recipeService.deleteRecipe(recipe);
    }

}
