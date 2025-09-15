package com.pragma.users.infrastructure.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "roles")
@Setter
@Getter
public class RoleProperties {
    private Map<String, String> map;

    public Map<String, String> getRoles() {
        return map;
    }

    public String getRoleName(String key) {
        return map.get(key);
    }
}
