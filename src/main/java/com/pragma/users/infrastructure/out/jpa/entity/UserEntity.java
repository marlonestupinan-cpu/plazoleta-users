package com.pragma.users.infrastructure.out.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "usuarios")
@NoArgsConstructor
@AllArgsConstructor
@Data
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
    @EqualsAndHashCode.Exclude
    private Date birthDate;
    @Column(name = "celular")
    private String phoneNumber;
    @Column(name = "clave")
    @EqualsAndHashCode.Exclude
    private String password;
    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false)
    private RoleEntity role;
    @ManyToOne
    @JoinColumn(name = "id_propietario")
    private UserEntity owner;
}
