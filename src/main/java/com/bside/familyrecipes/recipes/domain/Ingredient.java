package com.bside.familyrecipes.recipes.domain;

import com.bside.familyrecipes.common.domain.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Ingredient extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer orderNo;

    @Column(length = 500)
    private String name;

    @Column(length = 500)
    private String amount;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    @ToString.Exclude
    private Recipe recipe;


    @Column(columnDefinition = "char(1) default 'N'")
    private String requiredYn;

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void update(Ingredient ingredient) {
        this.name = ingredient.name;
        this.amount = ingredient.amount;
    }
}
