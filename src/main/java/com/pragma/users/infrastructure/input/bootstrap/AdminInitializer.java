package com.pragma.users.infrastructure.input.bootstrap;

import com.pragma.users.domain.api.IRoleServicePort;
import com.pragma.users.domain.api.IUserServicePort;
import com.pragma.users.domain.model.Role;
import com.pragma.users.domain.model.User;
import com.pragma.users.infrastructure.configuration.RoleProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {
    private final IUserServicePort userService;
    private final IRoleServicePort roleService;
    private final PasswordEncoder passwordEncoder;
    private final RoleProperties roleProperties;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        String roleName = roleProperties.getRoleName("admin");
        roleService.createIfNotExist(roleName);
        Role adminRole = roleService.getRoleByName(roleName);

        if (userService.getAdmin() == null) {
            User user = new User();
            LocalDate date = LocalDate.now().minusYears(20);
            Date birthDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());

            user.setName("Admin");
            user.setEmail(adminEmail);
            user.setPhoneNumber("+123123123");
            user.setBirthDate(birthDate);
            user.setPassword(passwordEncoder.encode(adminPassword));
            user.setRole(adminRole);

            userService.saveUser(user);
        }
    }
}
