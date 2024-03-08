package com.bside.familyrecipes.social.application;

import com.bside.familyrecipes.social.dto.request.SocialLoginRequestDTO;
import com.bside.familyrecipes.social.dto.response.SocialLoginResponseDTO;
import jakarta.security.auth.message.AuthException;

public interface SocialService {
    SocialLoginResponseDTO login(SocialLoginRequestDTO request);
}
