package com.pragma.users.application.mapper;

import com.pragma.users.application.dto.response.RoleResponseDto;
import com.pragma.users.domain.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRoleResponseMapper {
    RoleResponseDto toResponse(Role role);
    List<RoleResponseDto> toResponseList(List<Role> roleList);
}
