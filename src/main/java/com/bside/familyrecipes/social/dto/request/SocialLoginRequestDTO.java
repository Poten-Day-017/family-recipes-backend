package com.bside.familyrecipes.social.dto.request;

import com.bside.familyrecipes.social.enums.ProviderType;

public record SocialLoginRequestDTO(
        String token,
        ProviderType providerType,
        String deviceToken
) {
}
