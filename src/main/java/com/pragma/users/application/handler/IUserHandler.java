package com.pragma.users.application.handler;

import com.pragma.users.application.dto.request.UserRequestDto;
import com.pragma.users.application.dto.response.UserResponseDto;

import java.util.List;

public interface IUserHandler {
    void saveUser(UserRequestDto user);

    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserById(Long id);

    void updateUser(UserRequestDto user);

    void deleteUser(Long id);

    void createOwner(UserRequestDto user);

    boolean isOwner(Long id);
}
