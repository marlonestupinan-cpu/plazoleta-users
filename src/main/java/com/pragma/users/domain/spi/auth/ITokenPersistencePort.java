package com.pragma.users.domain.spi.auth;

import com.pragma.users.domain.model.User;
import com.pragma.users.domain.model.auth.Token;

import java.util.List;

public interface ITokenPersistencePort {
    void saveToken(User user, String token);

    List<Token> getAllActiveTokensByUser(User user);

    void saveAll(List<Token> validTokens);

    Token findByToken(String token);
}
