package com.bside.familyrecipes.recipes.application;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bside.familyrecipes.error.exception.RecipeNotFoundException;
import com.bside.familyrecipes.recipes.domain.Category;
import com.bside.familyrecipes.recipes.domain.Recipe;
import com.bside.familyrecipes.recipes.dto.request.RecipeCreateRequest;
import com.bside.familyrecipes.recipes.repository.IngredientRepository;
import com.bside.familyrecipes.recipes.repository.ProcedureRepository;
import com.bside.familyrecipes.recipes.repository.RecipeRepository;
import com.bside.familyrecipes.users.domain.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final ProcedureRepository procedureRepository;

    @Transactional(readOnly = true)
    public Page<Recipe> findRecipeList(Long userId, Pageable pageable) {
        return recipeRepository.findAll(pageable);
    }

    @Transactional
    public Recipe findRecipeById(Long recipeId) {
        var recipe = recipeRepository.findById(recipeId).orElseThrow(RecipeNotFoundException::new);
        var ingredientList = ingredientRepository.findAllByRecipeId(recipeId);
        var procedureList = procedureRepository.findAllByRecipeId(recipeId);
        recipe.setDetailInfo(ingredientList, procedureList);
        return recipe;
    }

    @Transactional(readOnly = true)
    public List<Category> findCategoryList() {
        return Arrays.stream(Category.values()).toList();
    }

    @Transactional
    public Long saveRecipe(User user, Recipe recipe, Map<String, String> storedFiles) {

        if (recipe.getOrderNo() == null) {
            var recipeList = findRecipeList(user.getId(), PageRequest.of(0, 1, Sort.by("createdAt").descending()));

            var orderNo = recipeList.get()
                .map(recipe1 -> recipe1.getOrderNo() + 1)
                .findFirst()
                .orElse(1);

            recipe.updateOrder(orderNo);
        }

        recipe.updateFileUrl(storedFiles);

        recipeRepository.save(recipe);
        ingredientRepository.saveAll(recipe.getIngredientList());
        procedureRepository.saveAll(recipe.getProcedureList());

        return recipe.getId();
    }

    @Transactional
    public List<Recipe> findRecipeByUserAndOrderNoGreaterThan(User user, Integer orderNo) {
        return recipeRepository.findRecipeByUserAndOrderNoGreaterThan(user, orderNo);
    }

    @Transactional
    public void saveAll(List<Recipe> recipes) {
        recipeRepository.saveAll(recipes);
    }

    @Transactional
    public void deleteRecipe(Recipe recipe) {
        procedureRepository.deleteAll(recipe.getProcedureList());
        ingredientRepository.deleteAll(recipe.getIngredientList());
        recipeRepository.delete(recipe);
    }
}
