package com.bside.familyrecipes.recipes.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bside.familyrecipes.recipes.domain.Recipe;

@Repository
public interface RecipeRepository extends PagingAndSortingRepository<Recipe, Long> {
    Optional<Recipe> findById(Long recipeId);
}
