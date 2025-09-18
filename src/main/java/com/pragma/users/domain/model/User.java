package com.pragma.users.domain.model;

import com.pragma.users.infrastructure.exception.IllegalUserAgeException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

@Data
@Getter
@Setter
public class User {
    private Long id;
    private String name;
    private String lastName;
    private String identityDocument;
    private String email;
    @EqualsAndHashCode.Exclude
    private Date birthDate;
    private String phoneNumber;
    @EqualsAndHashCode.Exclude
    private String password;
    private Role role;


    public void setBirthDate(Date birthDate) {
        LocalDate local = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now = LocalDate.now();
        int years = Period.between(local, now).getYears();

        if (years < 18) {
            throw new IllegalUserAgeException();
        }
        this.birthDate = birthDate;
    }
}
