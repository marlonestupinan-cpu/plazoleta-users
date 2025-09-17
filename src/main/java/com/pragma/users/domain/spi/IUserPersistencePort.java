package com.pragma.users.domain.spi;

import com.pragma.users.domain.model.Role;
import com.pragma.users.domain.model.User;

import java.util.List;

public interface IUserPersistencePort {
    void saveUser(User user);

    List<User> getAllUsers();

    User getUser(Long id);

    void updateUser(User user);

    void deleteUser(Long id);

    User findOneUserByRole(Role role);

    User getUserByEmail(String email);
}
