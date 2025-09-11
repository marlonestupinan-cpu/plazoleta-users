package com.pragma.users.infrastructure.configuration;

import com.pragma.users.domain.api.IObjectServicePort;
import com.pragma.users.domain.api.IUserServicePort;
import com.pragma.users.domain.spi.IObjectPersistencePort;
import com.pragma.users.domain.spi.IUserPersistencePort;
import com.pragma.users.domain.usecase.ObjectUseCase;
import com.pragma.users.domain.usecase.UserUseCase;
import com.pragma.users.infrastructure.out.jpa.adapter.ObjectJpaAdapter;
import com.pragma.users.infrastructure.out.jpa.mapper.IObjectEntityMapper;
import com.pragma.users.infrastructure.out.jpa.repository.IObjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IObjectRepository objectRepository;
    private final IObjectEntityMapper objectEntityMapper;

    @Bean
    public IObjectPersistencePort objectPersistencePort() {
        return new ObjectJpaAdapter(objectRepository, objectEntityMapper);
    }

//    @Bean
//    public IUserPersistencePort userPersistencePort() {
//
//    }

    @Bean
    public IObjectServicePort objectServicePort() {
        return new ObjectUseCase(objectPersistencePort());
    }

//    @Bean
//    public IUserServicePort userServicePort() {
//        return new UserUseCase(userPer());
//    }
}