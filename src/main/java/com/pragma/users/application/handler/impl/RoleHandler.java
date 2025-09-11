package com.pragma.users.application.handler.impl;

import com.pragma.users.application.dto.response.RoleResponseDto;
import com.pragma.users.application.handler.IRoleHandler;
import com.pragma.users.application.mapper.IRoleResponseMapper;
import com.pragma.users.domain.api.IRoleServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleHandler implements IRoleHandler {
    private final IRoleServicePort roleServicePort;
    private final IRoleResponseMapper roleResponseMapper;


    @Override
    public List<RoleResponseDto> getAllRoles() {
        return roleResponseMapper.toResponseList(roleServicePort.getAllRoles());
    }

    @Override
    public RoleResponseDto getRoleById(Long id) {
        return roleResponseMapper.toResponse(roleServicePort.getRole(id));
    }
}
