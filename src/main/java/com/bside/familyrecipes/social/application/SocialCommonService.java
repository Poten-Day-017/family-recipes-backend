package com.bside.familyrecipes.social.application;

import com.bside.familyrecipes.social.dto.request.SocialLoginRequestDTO;
import com.bside.familyrecipes.social.dto.response.SocialLoginResponseDTO;
import com.bside.familyrecipes.social.enums.ProviderType;

public interface SocialCommonService {
    SocialLoginResponseDTO DaeDaeSonSonLogin(SocialLoginRequestDTO socialLoginRequestDTO);
}

