package com.pragma.users.application.handler.impl;

import com.pragma.users.application.dto.request.UserRequestDto;
import com.pragma.users.application.dto.response.UserResponseDto;
import com.pragma.users.application.handler.IUserHandler;
import com.pragma.users.application.mapper.IRoleResponseMapper;
import com.pragma.users.application.mapper.IUserRequestMapper;
import com.pragma.users.application.mapper.IUserResponseMapper;
import com.pragma.users.domain.api.IUserServicePort;
import com.pragma.users.domain.model.User;
import lombok.RequiredArgsConstructor;
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
    private final IRoleResponseMapper roleResponseMapper;


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
        User oldUser = userServicePort.getUser(user.getId());

        User updatedUser = userRequestMapper.toUser(user);
        updatedUser.getRole().setId(oldUser.getRole().getId());

        userServicePort.updateUser(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        userServicePort.deleteUser(id);
    }
}
