package com.pragma.users.infrastructure.out.jpa.entity;

import com.pragma.users.domain.model.Role;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
