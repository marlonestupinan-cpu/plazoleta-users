package com.pragma.users.domain.usecase;

import com.pragma.users.domain.api.IRoleServicePort;
import com.pragma.users.domain.model.Role;
import com.pragma.users.domain.spi.IRolePersistencePort;

import java.util.List;

public class RoleUseCase implements IRoleServicePort {

    private final IRolePersistencePort rolePersistencePort;

    public RoleUseCase(IRolePersistencePort rolePersistencePort) {
        this.rolePersistencePort = rolePersistencePort;
    }

    @Override
    public void saveRole(Role role) {
        rolePersistencePort.saveRole(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return rolePersistencePort.getAllRoles();
    }

    @Override
    public Role getRole(Long id) {
        return rolePersistencePort.getRole(id);
    }

    @Override
    public void updateRole(Role role) {
        rolePersistencePort.updateRole(role);
    }

    @Override
    public void deleteRole(Long id) {
        rolePersistencePort.deleteRole(id);
    }
}
