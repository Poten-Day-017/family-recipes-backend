package com.bside.familyrecipes.oauth.jwt;

import com.bside.familyrecipes.config.AuthConfig;
import com.bside.familyrecipes.oauth.UserAuthentication;
import com.bside.familyrecipes.users.application.UserRepository;
import com.bside.familyrecipes.users.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Slf4j
class JwtTokenManagerTest {

    @Mock
    AuthConfig authConfig;
    @Mock
    UserRepository userRepository;

    private JwtTokenManager jwtTokenManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        jwtTokenManager = new JwtTokenManager(authConfig, userRepository);
    }

    @Test
    @DisplayName("JWT 토큰으로부터 UserAuthentication 객체를 가져온다.")
    void getClaimsFromToken() {
        // given
        Long userId = 1L;
        var user = User.builder().build();
        user.setId(userId);
        when(authConfig.getJwtSecretKey()).thenReturn("ZGFlZGFlc29uc29uLW9mLXBvdGVuZGF5LWp3dC1zZWNyZXQta2V5Cg==");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        String token = jwtTokenManager.createAccessToken(userId);
        log.info("[token]: {}", token);

        // when
        var userAuthentication = jwtTokenManager.getAuthentication(token);

        // then
        assertEquals(user.getId(), userAuthentication.getUserId());
    }

}