package com.bside.familyrecipes.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AppConfig {
    @Value("${familyRecipeBook.version}")
    private String appVersion;
}