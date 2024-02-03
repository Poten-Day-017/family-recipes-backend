package com.bside.familyrecipes.recipes.application;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bside.familyrecipes.error.exception.RecipeNotFoundException;
import com.bside.familyrecipes.recipes.domain.Category;
import com.bside.familyrecipes.recipes.domain.Recipe;
import com.bside.familyrecipes.recipes.repository.IngredientRepository;
import com.bside.familyrecipes.recipes.repository.ProcedureRepository;
import com.bside.familyrecipes.recipes.repository.RecipeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final ProcedureRepository procedureRepository;

    public Page<Recipe> findRecipeList(Long userId, Pageable pageable) {
        return recipeRepository.findAll(pageable);
    }

    public Recipe findRecipeById(Long userId, Long recipeId) {
        var recipe = recipeRepository.findById(recipeId).orElseThrow(RecipeNotFoundException::new);
        var ingredientList = ingredientRepository.findAllByRecipeId(recipeId);
        var procedureList = procedureRepository.findAllByRecipeId(recipeId);
        recipe.setDetailInfo(ingredientList, procedureList);
        return recipe;
    }

    public List<Category> findCategoryList() {
        return Arrays.stream(Category.values()).toList();
    }
}
