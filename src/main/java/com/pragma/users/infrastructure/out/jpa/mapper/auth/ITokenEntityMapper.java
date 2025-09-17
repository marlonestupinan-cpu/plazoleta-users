package com.pragma.users.infrastructure.out.jpa.mapper.auth;

import com.pragma.users.domain.model.auth.Token;
import com.pragma.users.infrastructure.out.jpa.entity.auth.TokenEntity;
import com.pragma.users.infrastructure.out.jpa.mapper.IRoleEntityMapper;
import com.pragma.users.infrastructure.out.jpa.mapper.IUserEntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = {IUserEntityMapper.class, IRoleEntityMapper.class})
public interface ITokenEntityMapper {
    Token toToken(TokenEntity tokenEntity);

    TokenEntity toTokenEntity(Token token);
}
