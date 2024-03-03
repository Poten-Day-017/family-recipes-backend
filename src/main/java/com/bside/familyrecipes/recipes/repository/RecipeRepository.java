package com.bside.familyrecipes.recipes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bside.familyrecipes.recipes.domain.Recipe;
import com.bside.familyrecipes.users.domain.User;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Optional<Recipe> findById(Long recipeId);

    List<Recipe> findRecipeByUserAndOrderNoGreaterThan(User user, Integer orderNo);
}
