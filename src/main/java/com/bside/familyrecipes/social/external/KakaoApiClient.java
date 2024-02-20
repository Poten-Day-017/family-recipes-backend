package com.bside.familyrecipes.social.external;

import com.bside.familyrecipes.social.dto.response.KakaoUserResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;


public interface KakaoApiClient {

    @GetMapping(value = "https://kapi.kakao.com/v2/user/me")
    KakaoUserResponse getUserInformation(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken);
}
