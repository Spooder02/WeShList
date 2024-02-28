package com.spooder.weshlist.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import lombok.*;

@RequiredArgsConstructor
@Component
public class JwtProvider {
    private static String secretKey;

    @Value("${spring.jwt.secret}")
    public void setSecretKey(String secretKey) {
        JwtProvider.secretKey = secretKey;
    }

    private long tokenVaildTime = 60 * 60 * 1000L; // Unit : ms

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        System.out.println("Excuted " + secretKey);
    }

    public String createToken(String id, String nickname, String userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("nickname", nickname);
        claims.put("userID", userId);
        
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(nickname)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenVaildTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            // 유효하지 않은 토큰
        }
        return false;
    }

    public static String parseJWT(String token, String claimName) {
        try {
            JwtParser parser = Jwts.parser()
                                    .setSigningKey(secretKey)
                                    .build();
            Claims claims = parser.parseClaimsJws(token)
                                    .getBody();
            return claims.get(claimName, String.class);
        } catch (Exception e) {
            // 토큰 파싱 실패 또는 검증 실패 시 예외 처리
            throw new IllegalArgumentException("Invalid JWT Token");
        }
    }
}
