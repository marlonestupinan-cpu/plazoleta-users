package com.pragma.users.domain.usecase;

import com.pragma.users.domain.api.IRoleServicePort;
import com.pragma.users.domain.model.Role;
import com.pragma.users.domain.spi.IRolePersistencePort;

public class RoleUserCase implements IRoleServicePort {
    private final IRolePersistencePort rolePersistencePort;

    public RoleUserCase(IRolePersistencePort rolePersistencePort) {
        this.rolePersistencePort = rolePersistencePort;
    }

    @Override
    public void createIfNotExist(String name) {
        if (rolePersistencePort.getRoleByName(name) == null) {
            Role role = new Role();
            role.setName(name);
            rolePersistencePort.saveRole(role);
        }
    }

    @Override
    public Role getRoleByName(String name) {
        return rolePersistencePort.getRoleByName(name);
    }
}
