package com.pragma.users.infrastructure.input.bootstrap;

import com.pragma.users.domain.api.IRoleServicePort;
import com.pragma.users.infrastructure.configuration.RoleProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RolDataInitializer implements CommandLineRunner {

    private final IRoleServicePort roleServicePort;
    private final RoleProperties roleProperties;

    public RolDataInitializer(IRoleServicePort roleServicePort, RoleProperties roleProperties) {
        this.roleServicePort = roleServicePort;
        this.roleProperties = roleProperties;
    }

    @Override
    public void run(String... args) {
        roleProperties.getRoles().values().forEach(roleServicePort::createIfNotExist);
    }
}
