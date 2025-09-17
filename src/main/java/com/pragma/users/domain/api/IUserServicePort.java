package com.pragma.users.domain.api;

import com.pragma.users.domain.model.User;

import java.util.List;

public interface IUserServicePort {
    void saveUser(User user);

    List<User> getAllUsers();

    User getUser(Long id);

    void updateUser(User user);

    void deleteUser(Long id);

    User getAdmin();

    User getUserByEmail(String username);
}
