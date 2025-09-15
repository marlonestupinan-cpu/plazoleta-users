package com.pragma.users.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Date;

@Getter
@Setter
public class UserRequestDto {
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @NotBlank
    @Pattern(regexp = "^\\d+$", message = "Debe contener solo números")
    private String identityDocument;
    @Email
    @NotBlank
    private String email;
    @Past
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date birthDate;
    @Size(min = 1, max = 13)
    @Pattern(regexp = "^\\+?\\d+$", message = "Debe contener solo números o el simboló \"+\"")
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String password;
}
