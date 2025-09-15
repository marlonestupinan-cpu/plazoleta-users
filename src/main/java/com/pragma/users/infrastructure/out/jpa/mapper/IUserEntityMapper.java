package com.pragma.users.infrastructure.out.jpa.mapper;

import com.pragma.users.domain.model.User;
import com.pragma.users.infrastructure.out.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = IRoleEntityMapper.class
)
public interface IUserEntityMapper {
    @Mapping(target = "role", source = "role")
    UserEntity toEntity(User user);

    @Mapping(target = "role", source = "role")
    User toUser(UserEntity userEntity);

    List<User> toUserList(List<UserEntity> userEntityList);
}
