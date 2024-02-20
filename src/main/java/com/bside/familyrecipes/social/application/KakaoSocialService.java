package com.bside.familyrecipes.social.application;

import com.bside.familyrecipes.social.dto.request.SocialLoginRequestDTO;
import com.bside.familyrecipes.social.dto.response.SocialLoginResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class KakaoSocialService implements SocialService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final SocialCommonService socialCommonService;

    @Override
    public SocialLoginResponseDTO login(SocialLoginRequestDTO socialLoginRequestDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + socialLoginRequestDTO.token());

        HttpEntity<SocialLoginRequestDTO> requestEntity =
                new HttpEntity<>(socialLoginRequestDTO, headers);

        ResponseEntity<String> response = restTemplate.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST, requestEntity, String.class);

        try {
            SocialLoginResponseDTO result = objectMapper.readValue(response.getBody(), SocialLoginResponseDTO.class);
            return socialCommonService.DaeDaeSonSonLogin(String.valueOf(result.id()), socialLoginRequestDTO.providerType(), socialLoginRequestDTO.deviceToken());
        } catch (Exception e) {
            throw new RuntimeException("응답을 SocialLoginResponseDTO로 변환하는 데 실패했습니다.", e);
        }
    }
}
