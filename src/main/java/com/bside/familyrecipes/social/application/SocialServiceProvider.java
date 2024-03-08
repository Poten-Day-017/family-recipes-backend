package com.bside.familyrecipes.social.application;

import com.bside.familyrecipes.social.enums.ProviderType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SocialServiceProvider {

    private static final Map<ProviderType, SocialService> socialServiceMap = new HashMap<>();

    private final KakaoSocialService kakaoSocialService;

    @PostConstruct
    void initializeSocialServiceMap() {
        socialServiceMap.put(ProviderType.KAKAO, kakaoSocialService);
    }

    public SocialService getSocialService(ProviderType providerType) {
        return socialServiceMap.get(providerType);
    }
}
