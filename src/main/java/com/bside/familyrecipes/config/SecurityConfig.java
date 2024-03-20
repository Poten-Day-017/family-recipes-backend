package com.bside.familyrecipes.config;

import static com.bside.familyrecipes.error.ErrorType.*;
import static org.springframework.util.MimeTypeUtils.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.bside.familyrecipes.common.dto.response.ErrorDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring()
            .requestMatchers("/v3/**", "/swagger-ui/**", "/favicon*/**", "/swagger-ui.html", "/actuator/**"));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .formLogin(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(request ->
                request.requestMatchers("/api/v1/**").permitAll()
                    .anyRequest().authenticated()
            )
            .exceptionHandling(configurer -> {
                configurer.accessDeniedHandler(
                    (request, response, accessDeniedException) -> authenticationFailResponse(response));
                configurer.authenticationEntryPoint(
                    (request, response, authException) -> authenticationFailResponse(response));
            })
            .build();
    }

    private void authenticationFailResponse(HttpServletResponse response) throws IOException {
        var errorDto = new ErrorDto(JWT_PAYMENT_REQUIRED.getMessage());
        var message = objectMapper.writeValueAsString(errorDto);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(JWT_PAYMENT_REQUIRED.getHttpStatus().value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(message);
    }

}
