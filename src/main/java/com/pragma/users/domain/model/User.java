package com.pragma.users.domain.model;

import com.pragma.users.infrastructure.exception.IllegalUserAgeException;
import lombok.Getter;
import lombok.Setter;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

@Getter
@Setter
public class User {
    private Long id;
    private String name;
    private String lastName;
    private String identityDocument;
    private String email;
    private Date birthDate;
    private String phoneNumber;
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

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
