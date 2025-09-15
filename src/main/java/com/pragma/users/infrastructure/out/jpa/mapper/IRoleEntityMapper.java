package com.pragma.users.infrastructure.out.jpa.mapper;

import com.pragma.users.domain.model.Role;
import com.pragma.users.infrastructure.out.jpa.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRoleEntityMapper {
    RoleEntity  toRoleEntity(Role role);
    Role toRole(RoleEntity roleEntity);
}
