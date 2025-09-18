package com.pragma.users.application.handler.impl.auth.jwt;

import com.pragma.users.application.handler.ITokenGenerator;
import com.pragma.users.domain.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;


@Service
public class JwtGenerator implements ITokenGenerator {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    @Override
    public String generateToken(User user) {
        return buildJwtToken(user, jwtExpiration);
    }

    @Override
    public String generateRefreshToken(User user) {
        return buildJwtToken(user, refreshExpiration);
    }

    @Override
    public String extractUsername(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    @Override
    public Date extractExpiration(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getExpiration();
    }

    @Override
    public boolean validateToken(String token, User user) {
        String username = extractUsername(token);
        return username.equals(user.getEmail()) && !validateTokenExpired(token);
    }

    @Override
    public boolean validateTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private String buildJwtToken(User user, long expiration) {
        return Jwts.builder()
                .id(user.getId().toString())
                .claim("id",  user.getId().toString())
                .claim("roles", user.getRole().getName().toUpperCase())
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();

    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
