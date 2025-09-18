package com.pragma.users.infrastructure.out.jpa.adapter.auth;

import com.pragma.users.domain.model.User;
import com.pragma.users.domain.model.auth.Token;
import com.pragma.users.domain.spi.auth.ITokenPersistencePort;
import com.pragma.users.infrastructure.out.jpa.mapper.auth.ITokenEntityMapper;
import com.pragma.users.infrastructure.out.jpa.repository.auth.ITokenRepository;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
public class TokenJpaAdapter implements ITokenPersistencePort {
    private final ITokenRepository tokenRepository;
    private final ITokenEntityMapper tokenEntityMapper;

    @Override
    public void saveToken(User user, String token) {
        Token newToken = Token.builder()
                .user(user)
                .token(token)
                .tokenType(Token.TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(tokenEntityMapper.toTokenEntity(newToken));
    }

    @Override
    public List<Token> getAllActiveTokensByUser(User user) {
        return tokenRepository
                .findAllExpiredIsFalseOrRevokedIsFalseByUserId(user.getId())
                .stream()
                .map(tokenEntityMapper::toToken)
                .collect(Collectors.toList());
    }


    @Override
    public void saveAll(List<Token> validTokens) {
        tokenRepository
                .saveAll(validTokens
                        .stream()
                        .map(tokenEntityMapper::toTokenEntity)
                        .collect(Collectors.toList())
                );
    }

    @Override
    public Token findByToken(String token) {
        return tokenRepository
                .findByToken(token)
                .map(tokenEntityMapper::toToken)
                .orElse(null);
    }
}
