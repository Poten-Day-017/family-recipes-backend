package com.bside.familyrecipes.social.application;

import com.bside.familyrecipes.social.dto.request.SocialLoginRequestDTO;
import com.bside.familyrecipes.social.dto.response.SocialLoginResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoSocialService implements SocialService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final SocialCommonService socialCommonService;

    @Override
    public SocialLoginResponseDTO login(SocialLoginRequestDTO socialLoginRequestDTO) {
        try {
            return socialCommonService.DaeDaeSonSonLogin(socialLoginRequestDTO);
        } catch (Exception e) {
            log.error("소셜 로그인 중 에러 = {}, 에러 메시지 = {}", e.getClass(), e.getMessage());
            throw new RuntimeException("응답을 SocialLoginResponseDTO로 변환하는 데 실패했습니다.", e);
        }
    }
}
