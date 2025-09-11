package com.pragma.users.application.mapper;

import com.pragma.users.application.dto.request.UserRequestDto;
import com.pragma.users.domain.model.Role;
import com.pragma.users.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserRequestMapper {
    UserRequestDto toDto(User user);
    User toUser(UserRequestDto userRequest);
    @Mapping(source = "userRequest.role.name", target = "name")
    Role toRole(UserRequestDto userRequest);
}
