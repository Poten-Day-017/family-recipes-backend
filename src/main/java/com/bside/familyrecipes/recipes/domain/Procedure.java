package com.bside.familyrecipes.recipes.domain;

import java.util.Map;

import org.springframework.util.StringUtils;

import com.bside.familyrecipes.common.domain.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "procedures")
public class Procedure extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer orderNo;

    @Column(length = 3000)
    private String description;

    @Column(length = 1000)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    @ToString.Exclude
    private Recipe recipe;

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void updateImageUrl(Map<String, String> storedFiles) {

        if (StringUtils.hasText(this.imageUrl)) {
            return;
        }

        this.imageUrl = storedFiles.get("procedureImage%d".formatted(orderNo));
    }

    public void update(Procedure procedure) {
        this.description = procedure.getDescription();
        this.imageUrl = procedure.getImageUrl();
    }
}
