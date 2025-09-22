package com.pragma.users.domain.model;

import com.pragma.users.domain.exception.DomainException;
import com.pragma.users.domain.exception.IllegalUserAgeException;
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
    private User owner = null;

    public void setPhoneNumber(String phone) {
        String regex = "^\\+?\\d+$";
        if (!phone.matches(regex)) {
            throw new DomainException("Formato de teléfono invalido");
        }
        if (phone.length() > 13) {
            throw new DomainException("Tamaño máximo de teléfono superado");
        }
        this.phoneNumber = phone;
    }

    public void setBirthDate(Date birthDate) {
        LocalDate local = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now = LocalDate.now();
        int years = Period.between(local, now).getYears();

        if (years < 18) {
            throw new IllegalUserAgeException();
        }
        this.birthDate = birthDate;
    }

    public void setIdentityDocument(String document) {
        String regex = "^\\d+$";
        if (!document.matches(regex)) {
            throw new DomainException("El documento debe solo contener números");
        }
        this.identityDocument = document;
    }

    public Long getOwnerId() {
        return owner == null ? null : owner.getId();
    }
}
