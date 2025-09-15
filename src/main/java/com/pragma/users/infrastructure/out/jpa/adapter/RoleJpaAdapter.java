package com.pragma.users.infrastructure.out.jpa.adapter;

import com.pragma.users.domain.model.Role;
import com.pragma.users.domain.spi.IRolePersistencePort;
import com.pragma.users.infrastructure.out.jpa.mapper.IRoleEntityMapper;
import com.pragma.users.infrastructure.out.jpa.repository.IRoleRepository;

public class RoleJpaAdapter implements IRolePersistencePort {
    private final IRoleRepository roleRepository;
    private final IRoleEntityMapper roleEntityMapper;

    public RoleJpaAdapter(IRoleRepository roleRepository, IRoleEntityMapper roleEntityMapper) {
        this.roleRepository = roleRepository;
        this.roleEntityMapper = roleEntityMapper;
    }


    @Override
    public void saveRole(Role role) {
        roleRepository.save(roleEntityMapper.toRoleEntity(role));
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name)
                .map(roleEntityMapper::toRole)
                .orElse(null);
    }
}
