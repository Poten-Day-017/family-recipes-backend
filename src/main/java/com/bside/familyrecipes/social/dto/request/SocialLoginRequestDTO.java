package com.bside.familyrecipes.social.dto.request;

import com.bside.familyrecipes.social.enums.ProviderType;

public record SocialLoginRequestDTO(
    String name,
    String email,
    String picture,
    ProviderType providerType,
    String deviceToken
){
}