package com.bside.familyrecipes.recipes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bside.familyrecipes.recipes.domain.Procedure;

@Repository
public interface ProcedureRepository extends JpaRepository<Procedure, Long> {
    List<Procedure> findAllByRecipeId(Long recipeId);
}
