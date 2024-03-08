package com.bside.familyrecipes.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Configuration
@Slf4j
public class RestTemplateConfig {
    String url = "https://kapi.kakao.com/v2/user/me";

    @Bean
    public org.springframework.web.client.RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
                .setConnectTimeout(Duration.ofMillis(5000)) // connection-timeout
                .setReadTimeout(Duration.ofMillis(5000)) // read-timeout
                .additionalMessageConverters(new StringHttpMessageConverter(StandardCharsets.UTF_8))
                .rootUri(String.format(url))
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
    }
}
