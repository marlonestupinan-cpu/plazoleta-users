package com.pragma.users.infrastructure.out.jpa.repository.auth;

import com.pragma.users.infrastructure.out.jpa.entity.auth.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ITokenRepository extends JpaRepository<TokenEntity, Long> {
    List<TokenEntity> findAllExpiredIsFalseOrRevokedIsFalseByUserId(Long userId);

    Optional<TokenEntity> findByToken(String token);
}
