package com.pragma.users.domain.api;

import com.pragma.users.domain.model.Role;

public interface IRoleServicePort {
    void createIfNotExist(String name);
    Role getRoleByName(String name);
}
