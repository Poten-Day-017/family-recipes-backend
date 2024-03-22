package com.bside.familyrecipes.oauth.jwt;

import com.bside.familyrecipes.config.AuthConfig;
import com.bside.familyrecipes.error.exception.BusinessException;
import com.bside.familyrecipes.oauth.UserAuthentication;
import com.bside.familyrecipes.users.application.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import jakarta.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import static com.bside.familyrecipes.error.ErrorType.UNAUTHORIZED_EXCEPTION;

@Slf4j
@RequiredArgsConstructor
@Service
public class JwtTokenManager {
    private final AuthConfig authConfig;
    private final UserRepository userRepository;
    private final ZoneId KST = ZoneId.of("Asia/Seoul");


    public String createAccessToken(Long userId) {
        val signatureAlgorithm = SignatureAlgorithm.HS256;
        val secretKeyBytes = DatatypeConverter.parseBase64Binary(authConfig.getJwtSecretKey());
        val signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
        val exp = ZonedDateTime.now(KST).toLocalDateTime().plusHours(6).atZone(KST).toInstant();

        return Jwts.builder()
                .setSubject(Long.toString(userId))
                .setExpiration(Date.from(exp))
                .signWith(signingKey, signatureAlgorithm)
                .compact();
    }

    public String createRefreshToken(Long userId) {
        val signatureAlgorithm = SignatureAlgorithm.HS256;
        val secretKeyBytes = DatatypeConverter.parseBase64Binary(authConfig.getJwtSecretKey());
        val signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
        val exp = ZonedDateTime.now(KST).toLocalDateTime().plusDays(14).atZone(KST).toInstant();

        return Jwts.builder()
                .setSubject(Long.toString(userId))
                .setExpiration(Date.from(exp))
                .signWith(signingKey, signatureAlgorithm)
                .compact();
    }

    public boolean verifyAuthToken(String token) {
        try {
            getClaimsFromToken(token);
            return true;
        } catch ( SignatureException | ExpiredJwtException e ) {
            log.error("Token is invalid or expired", e);
            return false;
        }
    }

    private Claims getClaimsFromToken(String token) throws SignatureException {
        return Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(authConfig.getJwtSecretKey()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public UserAuthentication getAuthentication(String token) {
        val claims = getClaimsFromToken(token);
        val userId = Long.parseLong(claims.getSubject());
        val user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(UNAUTHORIZED_EXCEPTION));
        return new UserAuthentication(user);
    }
}
