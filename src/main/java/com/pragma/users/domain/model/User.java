package com.pragma.users.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String name;
    private String lastName;
    private String identityDocument;
    private String email;
    private Date birthDate;
    private String phoneNumber;
    private String password;
}
