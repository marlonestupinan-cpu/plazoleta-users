package com.pragma.users.application.handler.impl;

import com.pragma.users.application.dto.request.UserRequestDto;
import com.pragma.users.application.dto.response.UserResponseDto;
import com.pragma.users.application.handler.IUserHandler;
import com.pragma.users.application.mapper.IUserRequestMapper;
import com.pragma.users.application.mapper.IUserResponseMapper;
import com.pragma.users.domain.api.IRoleServicePort;
import com.pragma.users.domain.api.IUserServicePort;
import com.pragma.users.domain.model.Role;
import com.pragma.users.domain.model.User;
import com.pragma.users.infrastructure.configuration.RoleProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserHandler implements IUserHandler {
    private final IUserServicePort userServicePort;
    private final IUserRequestMapper userRequestMapper;
    private final IUserResponseMapper userResponseMapper;

    private final IRoleServicePort roleServicePort;
    private final RoleProperties roleProperties;

    private final PasswordEncoder encoder;

    @Override
    public void saveUser(UserRequestDto user) {
        userServicePort.saveUser(userRequestMapper.toUser(user));
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userResponseMapper.toResponseList(userServicePort.getAllUsers());
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        return userResponseMapper.toResponse(userServicePort.getUser(id));
    }

    @Override
    public void updateUser(UserRequestDto user) {
        User updatedUser = userRequestMapper.toUser(user);
        userServicePort.updateUser(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        userServicePort.deleteUser(id);
    }

    @Override
    public void createOwner(UserRequestDto user) {
        User newOwner = userRequestMapper.toUser(user);
        Role role = roleServicePort.getRoleByName(roleProperties.getRoleName("owner"));
        newOwner.setRole(role);
        newOwner.setPassword(encoder.encode(user.getPassword()));
        userServicePort.saveUser(newOwner);
    }

    @Override
    public boolean isOwner(Long id) {
        User user = userServicePort.getUser(id);
        Role ownerRole = roleServicePort.getRoleByName(roleProperties.getRoleName("owner"));
        return user.getRole().getId().equals(ownerRole.getId());
    }

    @Override
    public void createEmployee(UserRequestDto user) {
        User newOwner = userRequestMapper.toUser(user);
        Role role = roleServicePort.getRoleByName(roleProperties.getRoleName("employee"));
        newOwner.setRole(role);
        newOwner.setPassword(encoder.encode(user.getPassword()));
        userServicePort.saveUser(newOwner);
    }

    @Override
    public void createClient(UserRequestDto user) {
        User newOwner = userRequestMapper.toUser(user);
        Role role = roleServicePort.getRoleByName(roleProperties.getRoleName("client"));
        newOwner.setRole(role);
        newOwner.setPassword(encoder.encode(user.getPassword()));
        userServicePort.saveUser(newOwner);
    }
}
