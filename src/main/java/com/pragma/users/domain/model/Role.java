package com.pragma.users.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Getter
    @AllArgsConstructor
    public enum type {
        ADMIN("admin"),
        OWNER("owner"),
        EMPLOYEE("employee"),
        CLIENT("client");

        private final String code;
    }

    private Long id;
    private String name;
    private String description;
}