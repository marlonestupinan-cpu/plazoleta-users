package com.pragma.users.domain.usecase.auth;

import com.pragma.users.domain.api.auth.ITokenServicePort;
import com.pragma.users.domain.model.User;
import com.pragma.users.domain.model.auth.Token;
import com.pragma.users.domain.spi.auth.ITokenPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TokenUseCase implements ITokenServicePort {
    private final ITokenPersistencePort tokenPersistencePort;
    @Override
    public void saveToken(User user, String token) {
        tokenPersistencePort.saveToken(user, token);
    }

    @Override
    public void revokeAllUsersToken(User user) {
        List<Token> validTokens = tokenPersistencePort.getAllActiveTokensByUser(user);
        for (Token validToken : validTokens) {
            validToken.setExpired(true);
            validToken.setRevoked(true);
        }
        tokenPersistencePort.saveAll(validTokens);
    }

    @Override
    public Token findByToken(String token) {
        return tokenPersistencePort.findByToken(token);
    }
}
