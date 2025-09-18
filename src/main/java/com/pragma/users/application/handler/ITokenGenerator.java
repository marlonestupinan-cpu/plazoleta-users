package com.pragma.users.application.handler;

import com.pragma.users.domain.model.User;

import java.util.Date;

public interface ITokenGenerator {
    String generateToken(User user);
    String generateRefreshToken(User user);
    String extractUsername(String token);
    Date extractExpiration(String token);
    boolean validateToken(String token, User user);
    boolean validateTokenExpired(String token);
}
