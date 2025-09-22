package com.pragma.users.infrastructure.configuration;

import com.pragma.users.application.handler.ITokenGenerator;
import com.pragma.users.application.handler.impl.auth.jwt.JwtGenerator;
import com.pragma.users.domain.api.IPasswordEncoderPort;
import com.pragma.users.domain.api.IRolePropiertiesPort;
import com.pragma.users.domain.api.IRoleServicePort;
import com.pragma.users.domain.api.IUserServicePort;
import com.pragma.users.domain.api.auth.ITokenServicePort;
import com.pragma.users.domain.spi.IRolePersistencePort;
import com.pragma.users.domain.spi.IUserPersistencePort;
import com.pragma.users.domain.spi.auth.ITokenPersistencePort;
import com.pragma.users.domain.usecase.RoleUserCase;
import com.pragma.users.domain.usecase.UserUseCase;
import com.pragma.users.domain.usecase.auth.TokenUseCase;
import com.pragma.users.infrastructure.out.jpa.adapter.RoleJpaAdapter;
import com.pragma.users.infrastructure.out.jpa.adapter.UserJpaAdapter;
import com.pragma.users.infrastructure.out.jpa.adapter.auth.TokenJpaAdapter;
import com.pragma.users.infrastructure.out.jpa.mapper.IRoleEntityMapper;
import com.pragma.users.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.users.infrastructure.out.jpa.mapper.auth.ITokenEntityMapper;
import com.pragma.users.infrastructure.out.jpa.repository.IRoleRepository;
import com.pragma.users.infrastructure.out.jpa.repository.IUserRepository;
import com.pragma.users.infrastructure.out.jpa.repository.auth.ITokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    private final IRoleRepository roleRepository;
    private final IRoleEntityMapper roleEntityMapper;

    private final ITokenRepository tokenRepository;
    private final ITokenEntityMapper tokenEntityMapper;

    private final RoleProperties roleProperties;

    private final @Lazy PasswordEncoder passwordEncoder;

    @Bean
    public IPasswordEncoderPort passwordEncoderPort() {
        return passwordEncoder::encode;
    }

    @Bean
    public IRolePropiertiesPort rolePropertiesPort() {
        return roleProperties::getRoleName;
    }

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserJpaAdapter(userRepository, userEntityMapper, roleEntityMapper);
    }

    @Bean
    public IRolePersistencePort rolePersistencePort() {
        return new RoleJpaAdapter(roleRepository, roleEntityMapper);
    }

    @Bean
    public ITokenPersistencePort tokenPersistencePort() {
        return new TokenJpaAdapter(tokenRepository, tokenEntityMapper);
    }

    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(userPersistencePort(), roleServicePort(), rolePropertiesPort(), passwordEncoderPort());
    }

    @Bean
    public IRoleServicePort roleServicePort() {
        return new RoleUserCase(rolePersistencePort());
    }

    @Bean
    public ITokenServicePort tokenServicePort() {
        return new TokenUseCase(tokenPersistencePort());
    }

    @Bean
    public ITokenGenerator tokenGenerator() {
        return new JwtGenerator();
    }
}