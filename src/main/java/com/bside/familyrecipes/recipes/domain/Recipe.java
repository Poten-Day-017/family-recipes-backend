package com.bside.familyrecipes.recipes.domain;

import java.util.ArrayList;
import java.util.List;

import com.bside.familyrecipes.common.domain.BaseEntity;
import com.bside.familyrecipes.recipes.converter.CategoryAttributeConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Recipe extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // FIXME User Entity
    private Long userId;
    // FIXME RecipeFiles
    @Column(length = 1000)
    private String cookingImageUrl;
    @Column(length = 1000)
    private String cookingVideoUrl;

    @Column(length = 1000)
    private String title;

    @Column(length = 500)
    private String origin;

    @Column(length = 3000)
    private String content;

    @Convert(converter = CategoryAttributeConverter.class)
    private Category category;

    private Integer capacity;

    @Column(length = 3000)
    private String episode;

    @Column(columnDefinition = "char(1) default 'Y'")
    private String episodeOpenYn;

    @Column(columnDefinition = "char(1) default 'Y'")
    private String totalOpenYn;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
    private final List<Ingredient> ingredientList = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
    private final List<Procedure> procedureList = new ArrayList<>();

    @Builder
    public Recipe(Long id, Long userId, String cookingImageUrl, String cookingVideoUrl, String title, String origin,
        String content, Category category, Integer capacity, String episode, String episodeOpenYn, String totalOpenYn) {
        this.id = id;
        this.userId = userId;
        this.cookingImageUrl = cookingImageUrl;
        this.cookingVideoUrl = cookingVideoUrl;
        this.title = title;
        this.origin = origin;
        this.content = content;
        this.category = category;
        this.capacity = capacity;
        this.episode = episode;
        this.episodeOpenYn = episodeOpenYn;
        this.totalOpenYn = totalOpenYn;
    }

    public void setDetailInfo(List<Ingredient> ingredientList, List<Procedure>procedureList) {
        this.ingredientList.addAll(ingredientList);
        this.procedureList.addAll(procedureList);
    }

    public List<Ingredient> findIngredientList() {
        return this.ingredientList.stream()
            .filter(ingredient -> "N".equals(ingredient.getRequiredYn()))
            .toList();
    }

    public List<Ingredient> findSecretIngredientList() {
        return this.ingredientList.stream()
            .filter(ingredient -> "Y".equals(ingredient.getRequiredYn()))
            .toList();
    }

}
