package com.example.msa.common.util;

import com.example.msa.common.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * packageName    : com.example.msa.common.util
 * fileName       : JwtUtil
 * author         : 'stz'
 * date           : 25. 12. 20.
 * description    : JWT 토큰 생성 및 검증 유틸리티
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 12. 10.          'stz'       최초 생성
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String username) {
        EgovMap claims = new EgovMap();
        return createToken(claims, username);
    }

    private String createToken(EgovMap claims, String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    
    public Token makeTokenType(EgovMap userInfo, String token) {
        return Token.builder()
                .userId(userInfo.getString("userId"))
                .ip(userInfo.getString("ip"))
                .email(userInfo.getString("email"))
                .joinDt(userInfo.getString("joinDt"))
                .roleCd(userInfo.getString("roleCd"))
                .userNm(userInfo.getString("userNm"))
                .password(userInfo.getString("password"))
                .accessToken(token)
                .build();
    }

    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}