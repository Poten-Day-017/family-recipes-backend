package com.bside.familyrecipes.social.application;

import com.bside.familyrecipes.config.AppConfig;
import com.bside.familyrecipes.config.jwt.JwtTokenManager;
import com.bside.familyrecipes.social.domain.AuthProvider;
import com.bside.familyrecipes.social.dto.response.SocialLoginResponseDTO;
import com.bside.familyrecipes.social.enums.ProviderType;
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

    @Transactional
    private SocialLoginResponseDTO SignUpAndLogin(
            String thirdPartyUserId,
            ProviderType providerType,
            String deviceToken
    ) {
        val user = userRepository.save(User.builder()
                .build());

        val userId = user.getId();
        val accessToken = jwtTokenManager.createAccessToken(userId);
        val refreshToken = jwtTokenManager.createRefreshToken(userId);

        authProviderRepository.save(AuthProvider.builder()
                .id(thirdPartyUserId)
                .user(user)
                .providerType(providerType)
                .build());

        saveDeviceToken(user, deviceToken);

        return SocialLoginResponseDTO.of( false, userId, accessToken, refreshToken, appConfig.getAppVersion());
    }

    private void saveDeviceToken(User user, String deviceToken) {
        user.updateDeviceToken(deviceToken);
    }

    @Override
    @Transactional
    public SocialLoginResponseDTO DaeDaeSonSonLogin(String thirdPartyUserId, ProviderType providerType, String deviceToken) {

        return SignUpAndLogin(thirdPartyUserId, providerType, deviceToken);
    }
}
