package com.bside.familyrecipes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FamilyRecipesApplication {

    public static void main(String[] args) {
        SpringApplication.run(FamilyRecipesApplication.class, args);
    }

}
