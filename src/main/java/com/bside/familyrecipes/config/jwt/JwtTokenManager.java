package com.bside.familyrecipes.config.jwt;

import com.bside.familyrecipes.common.exception.AuthException;
import com.bside.familyrecipes.common.message.ExceptionMessage;
import com.bside.familyrecipes.config.AuthConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import jakarta.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class JwtTokenManager {
    private final AuthConfig authConfig;
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

    public boolean verifyAuthToken (String token) {
        try {
            getClaimsFromToken(token);
            return true;
        } catch (SignatureException | ExpiredJwtException e) {
            return false;
        }
    }

    public String getUserIdFromAuthToken (String token) {
        try {
            val claims = getClaimsFromToken(token);

            return claims.getSubject();
        } catch (SignatureException | ExpiredJwtException e) {
            throw new AuthException(ExceptionMessage.INVALID_TOKEN.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private Claims getClaimsFromToken (String token) throws SignatureException {
        return Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(authConfig.getJwtSecretKey()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
