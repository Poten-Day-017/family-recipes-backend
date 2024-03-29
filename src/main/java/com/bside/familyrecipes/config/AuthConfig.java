package com.bside.familyrecipes.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AuthConfig {
    @Value("${jwt.secret}")
    private String jwtSecretKey;

    @Value("${kakao.redirect.uri}")
    private String kakaoRedirectUri;

    @Value("${kakao.client.secret}")
    private String kakaoClientSecret;
}
