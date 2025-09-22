package com.pragma.users.domain.api;

import com.pragma.users.domain.model.Role;
import com.pragma.users.domain.model.User;

import java.util.List;

public interface IUserServicePort {
    void saveUser(User user);
    void saveUser(User user, Role role);
    void saveUser(User user, Role role, User owner);

    List<User> getAllUsers();

    User getUser(Long id);

    void updateUser(User user);

    void deleteUser(Long id);

    User getAdmin();

    User getUserByEmail(String username);
}
