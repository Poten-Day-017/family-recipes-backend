package com.bside.familyrecipes.users.domain;

import com.bside.familyrecipes.recipes.domain.Recipe;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "\"User\"")
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_nickname")
    private String userNickname;

    @OneToMany(mappedBy = "user")
    List<Recipe> recipes = new ArrayList<>();
}
