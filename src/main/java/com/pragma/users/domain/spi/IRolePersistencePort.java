package com.pragma.users.domain.spi;

import com.pragma.users.domain.model.Role;

public interface IRolePersistencePort {
    void saveRole(Role role);

    Role getRoleByName(String name);
}
