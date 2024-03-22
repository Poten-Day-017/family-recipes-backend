package com.bside.familyrecipes.social.application;

import com.bside.familyrecipes.config.AppConfig;
import com.bside.familyrecipes.oauth.jwt.JwtTokenManager;
import com.bside.familyrecipes.social.domain.AuthProvider;
import com.bside.familyrecipes.social.dto.request.SocialLoginRequestDTO;
import com.bside.familyrecipes.social.dto.response.SocialLoginResponseDTO;
import com.bside.familyrecipes.users.application.UserRepository;
import com.bside.familyrecipes.users.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class SocialCommonServiceImpl implements SocialCommonService{

    private final UserRepository userRepository;
    private final JwtTokenManager jwtTokenManager;
    private final AuthProviderRepository authProviderRepository;
    private final AppConfig appConfig;

    private SocialLoginResponseDTO reLogin(User user, String deviceToken) {
        val userId = user.getId();
        val accessToken = jwtTokenManager.createAccessToken(userId);
        val refreshToken = jwtTokenManager.createRefreshToken(userId);

        saveDeviceToken(user, deviceToken);

        return SocialLoginResponseDTO.of(true, userId, accessToken, refreshToken, appConfig.getAppVersion());
    }

    @Transactional
    protected SocialLoginResponseDTO SignUpAndLogin(
            SocialLoginRequestDTO socialLoginRequestDTO
    ) {
        val user = userRepository.save(User.builder()
                        .userNickname(socialLoginRequestDTO.name())
                        .profileImage(socialLoginRequestDTO.picture())
                .build());

        val userId = user.getId();
        val accessToken = jwtTokenManager.createAccessToken(userId);
        val refreshToken = jwtTokenManager.createRefreshToken(userId);

        authProviderRepository.save(AuthProvider.builder()
                .id(socialLoginRequestDTO.email())
                .user(user)
                .providerType(socialLoginRequestDTO.providerType())
                .build());

        saveDeviceToken(user, socialLoginRequestDTO.deviceToken());

        return SocialLoginResponseDTO.of( false, userId, accessToken, refreshToken, appConfig.getAppVersion());
    }

    private void saveDeviceToken(User user, String deviceToken) {
        user.updateDeviceToken(deviceToken);
    }

    @Override
    @Transactional
    public SocialLoginResponseDTO DaeDaeSonSonLogin(SocialLoginRequestDTO socialLoginRequestDTO) {
        val authProvider = authProviderRepository.searchAuthProviderById(socialLoginRequestDTO.email());

        if(Objects.nonNull(authProvider)) {
            return reLogin(authProvider.getUser(), socialLoginRequestDTO.deviceToken());
        }

        return SignUpAndLogin(socialLoginRequestDTO);
    }
}
