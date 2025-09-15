package com.pragma.users.infrastructure.out.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "usuarios")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre")
    private String name;
    @Column(name = "apellido")
    private String lastName;
    @Column(name = "numero_documento")
    private String identityDocument;
    @Column(name = "correo")
    private String email;
    @Column(name = "fecha_nacimiento")
    private Date birthDate;
    @Column(name = "celular")
    private String phoneNumber;
    @Column(name = "clave")
    private String password;
    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false)
    private RoleEntity role;
}
