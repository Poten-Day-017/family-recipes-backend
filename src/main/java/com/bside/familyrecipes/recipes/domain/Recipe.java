package com.bside.familyrecipes.recipes.domain;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bside.familyrecipes.common.domain.BaseEntity;
import com.bside.familyrecipes.recipes.converter.CategoryAttributeConverter;
import com.bside.familyrecipes.users.domain.User;

import jakarta.persistence.*;
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

    private Integer orderNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

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

    @Column(columnDefinition = "char(1) default 'Y'")
    private String totalOpenYn;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
    private final List<Ingredient> ingredientList = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
    private final List<Procedure> procedureList = new ArrayList<>();

    @Builder
    public Recipe(Long id, Integer orderNo, User user, String cookingImageUrl, String cookingVideoUrl, String title, String origin,
        String content, Category category, Integer capacity, String totalOpenYn) {
        this.id = id;
        this.orderNo = orderNo;
        this.user = user;
        this.cookingImageUrl = cookingImageUrl;
        this.cookingVideoUrl = cookingVideoUrl;
        this.title = title;
        this.origin = origin;
        this.content = content;
        this.category = category;
        this.capacity = capacity;
        this.totalOpenYn = totalOpenYn;
    }

    public void setDetailInfo(List<Ingredient> ingredientList, List<Procedure>procedureList) {
        ingredientList.forEach(this::addIngredientList);
        procedureList.forEach(this::addProcedureList);
    }

    public List<Ingredient> findIngredientList() {
        return this.ingredientList.stream()
            .filter(ingredient -> "Y".equals(ingredient.getRequiredYn()))
            .toList();
    }

    public List<Ingredient> findSecretIngredientList() {
        return this.ingredientList.stream()
            .filter(ingredient -> "N".equals(ingredient.getRequiredYn()))
            .toList();
    }

    public void updateOrder(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public void updateFileUrl(Map<String, String> storedFiles) {
        this.cookingImageUrl = storedFiles.get("cookingImage");
        this.cookingVideoUrl = storedFiles.get("cookingVideo");

        for (Procedure procedure : procedureList) {
            var orderNo1 = procedure.getOrderNo();
            procedure.setImageUrl("procedureImage%d".formatted(orderNo1));
        }
    }

    public String getFormattedCreatedAt() {
        return this.getCreatedAt() == null ? null
            : this.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }

    public void addIngredientList(Ingredient ingredient) {

        if (ingredient.getRecipe() != this) {
            ingredient.setRecipe(this);
        }

        this.ingredientList.add(ingredient);
    }

    public void addProcedureList(Procedure procedure) {

        if (procedure.getRecipe() != this) {
            procedure.setRecipe(this);
        }

        this.procedureList.add(procedure);
    }

}
