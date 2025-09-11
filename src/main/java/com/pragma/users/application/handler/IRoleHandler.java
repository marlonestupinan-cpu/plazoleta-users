package com.pragma.users.application.handler;

import com.pragma.users.application.dto.response.RoleResponseDto;

import java.util.List;

public interface IRoleHandler {
    List<RoleResponseDto> getAllRoles();
    RoleResponseDto getRoleById(Long id);
}
