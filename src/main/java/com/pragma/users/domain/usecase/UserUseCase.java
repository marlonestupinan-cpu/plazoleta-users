package com.pragma.users.domain.usecase;

import com.pragma.users.domain.api.IPasswordEncoderPort;
import com.pragma.users.domain.api.IRolePropiertiesPort;
import com.pragma.users.domain.api.IRoleServicePort;
import com.pragma.users.domain.api.IUserServicePort;
import com.pragma.users.domain.model.Role;
import com.pragma.users.domain.model.User;
import com.pragma.users.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserUseCase implements IUserServicePort {
    private final IUserPersistencePort userPersistencePort;
    private final IRoleServicePort roleService;
    private final IRolePropiertiesPort roleProperties;
    private final IPasswordEncoderPort encoder;

    @Override
    public void saveUser(User user) {
        saveUser(user, null, null);
    }

    @Override
    public void saveUser(User user, Role role) {
        user.setRole(role);
        saveUser(user, role, null);
    }

    @Override
    public void saveUser(User user, Role role, User owner) {
        user.setOwner(owner);
        user.setPassword(encoder.encode(user.getPassword()));
        userPersistencePort.saveUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userPersistencePort.getAllUsers();
    }

    @Override
    public User getUser(Long id) {
        return userPersistencePort.getUser(id);
    }

    @Override
    public void updateUser(User user) {
        userPersistencePort.updateUser(user);
    }

    @Override
    public void deleteUser(Long id) {
        userPersistencePort.deleteUser(id);
    }

    @Override
    public User getAdmin() {
        Role role = roleService.getRoleByName(roleProperties.getRoleName(Role.type.ADMIN.getCode()));
        return userPersistencePort.findOneUserByRole(role);
    }

    @Override
    public User getUserByEmail(String email) {
        return userPersistencePort.getUserByEmail(email);
    }
}
