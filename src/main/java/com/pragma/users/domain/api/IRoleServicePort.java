package com.pragma.users.domain.api;


import com.pragma.users.domain.model.Role;

import java.util.List;

public interface IRoleServicePort {
    void saveRole(Role role);

    List<Role> getAllRoles();

    Role getRole(Long id);

    void updateRole(Role role);

    void deleteRole(Long id);
}
