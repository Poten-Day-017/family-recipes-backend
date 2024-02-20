package com.bside.familyrecipes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@OpenAPIDefinition(info = @Info(title = "가족 레시피 북 API 명세서", version = "v1")
)
@Configuration
public class SwaggerConfig {

    public static final String JWT_PREFIX = "Bearer";
    public static final String JWT_TYPE = "JWT";
    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Bean
    public OpenAPI openAPI() {
        var securityRequirement = new SecurityRequirement().addList(AUTHORIZATION_HEADER);
        var components = new Components()
                .addSecuritySchemes(AUTHORIZATION_HEADER, new SecurityScheme()
                        .name(AUTHORIZATION_HEADER)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme(JWT_PREFIX)
                        .bearerFormat(JWT_TYPE));
        return new OpenAPI()
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}
