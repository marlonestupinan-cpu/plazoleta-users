package com.pragma.users.domain.usecase;

import com.pragma.users.domain.api.IUserServicePort;
import com.pragma.users.domain.model.User;
import com.pragma.users.domain.spi.IUserPersistencePort;

import java.util.List;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;

    public UserUseCase(IUserPersistencePort userPersistencePort) {
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    public void saveUser(User user) {
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
}
