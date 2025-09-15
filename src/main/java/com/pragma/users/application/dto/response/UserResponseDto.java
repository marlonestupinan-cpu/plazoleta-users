package com.pragma.users.application.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String name;
    private String lastName;
    private String identityDocument;
    private String email;
    private Date birthDate;
    private String phoneNumber;
}
