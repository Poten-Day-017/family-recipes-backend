package com.bside.familyrecipes.recipes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bside.familyrecipes.recipes.domain.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Optional<Recipe> findById(Long recipeId);
}
