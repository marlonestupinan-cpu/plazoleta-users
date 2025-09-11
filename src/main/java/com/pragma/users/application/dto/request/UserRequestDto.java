package com.pragma.users.application.dto.request;

import com.pragma.users.domain.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserRequestDto {
    private Long id;
    private String name;
    private String lastName;
    private String identityDocument;
    private String email;
    private Date birthDate;
    private String phoneNumber;
    private String password;
    private Role role;
}
