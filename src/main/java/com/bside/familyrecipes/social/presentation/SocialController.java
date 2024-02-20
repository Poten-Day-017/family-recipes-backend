package com.bside.familyrecipes.social.presentation;

import com.bside.familyrecipes.common.dto.response.ResponseDto;
import com.bside.familyrecipes.social.application.SocialCommonService;
import com.bside.familyrecipes.social.application.SocialServiceProvider;
import com.bside.familyrecipes.social.dto.request.SocialLoginRequestDTO;

import com.bside.familyrecipes.social.dto.response.SocialLoginResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/social")
public class SocialController {
    private final SocialServiceProvider socialServiceProvider;
    private final SocialCommonService socialCommonService;

    @PostMapping("/login")
    public ResponseEntity<SocialLoginResponseDTO> login(@RequestBody SocialLoginRequestDTO request) {
        val socialService = socialServiceProvider.getSocialService(request.providerType());
        val response = socialService.login(request);
        return ResponseDto.ok(response);
    }
}
