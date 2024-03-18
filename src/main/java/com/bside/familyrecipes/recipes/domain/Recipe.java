package com.bside.familyrecipes.recipes.domain;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.bside.familyrecipes.common.domain.BaseEntity;
import com.bside.familyrecipes.recipes.converter.CategoryAttributeConverter;
import com.bside.familyrecipes.users.domain.User;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@ToString
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

    @OneToMany(mappedBy = "recipe",
        fetch = FetchType.LAZY,
        cascade = CascadeType.PERSIST,
        orphanRemoval = true)
    private final List<Ingredient> ingredientList = new ArrayList<>();

    @OneToMany(mappedBy = "recipe",
        fetch = FetchType.LAZY,
        cascade = CascadeType.PERSIST,
        orphanRemoval = true)
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

        if (!StringUtils.hasText(this.cookingImageUrl)) {
            this.cookingImageUrl = storedFiles.get("cookingImage");
        }

        if (!StringUtils.hasText(this.cookingVideoUrl)) {
            this.cookingVideoUrl = storedFiles.get("cookingVideo");
        }

        this.procedureList.forEach(procedure -> procedure.updateImageUrl(storedFiles));
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

    public void update(Recipe requestEntity) {
        this.title = requestEntity.getTitle();
        this.content = requestEntity.getContent();
        this.cookingImageUrl = requestEntity.getCookingImageUrl();
        this.cookingVideoUrl = requestEntity.getCookingVideoUrl();
        this.origin = requestEntity.getOrigin();
        this.category = requestEntity.getCategory();
        this.capacity = requestEntity.getCapacity();
        this.totalOpenYn = requestEntity.getTotalOpenYn();

        updateProcedureList(requestEntity);
        updateIngredientList(requestEntity);
    }

    private void updateIngredientList(Recipe requestEntity) {
        this.ingredientList.removeIf(ingredient1 ->
            requestEntity.getIngredientList().stream()
                .filter(requestIngredient -> requestIngredient.getOrderNo().equals(ingredient1.getOrderNo())
                    && requestIngredient.getRequiredYn().equals(ingredient1.getRequiredYn()))
                .findFirst().isEmpty());

        this.ingredientList.forEach(ingredient1 -> {
            var first = requestEntity.getIngredientList().stream()
                .filter(requestIngredient -> requestIngredient.getOrderNo().equals(ingredient1.getOrderNo())
                    && requestIngredient.getRequiredYn().equals(ingredient1.getRequiredYn()))
                .findFirst();
            if (first.isEmpty()) {
                return;
            }
            var i = first.get();
            ingredient1.update(i);
            requestEntity.ingredientList.remove(i);
        });

        requestEntity.ingredientList.forEach(this::addIngredientList);
    }

    private void updateProcedureList(Recipe requestEntity) {
        this.procedureList.removeIf(procedure1 ->
            requestEntity.getProcedureList()
                .stream()
                .filter(requestProcedure -> requestProcedure.getOrderNo().equals(procedure1.getOrderNo()))
                .findAny().isEmpty());

        this.procedureList.forEach(procedure1 -> {
            var first = requestEntity.getProcedureList().stream()
                .filter(requestProcedure -> requestProcedure.getOrderNo().equals(procedure1.getOrderNo()))
                .findFirst();
            if (first.isEmpty()) {
                return;
            }
            var p = first.get();
            procedure1.update(p);
            requestEntity.procedureList.remove(p);
        });

        requestEntity.procedureList.forEach(this::addProcedureList);
    }

}
