package com.pragma.users.infrastructure.input.rest;


import com.pragma.users.infrastructure.configuration.security.CustomUserDetails;
import com.pragma.users.application.dto.request.UserRequestDto;
import com.pragma.users.application.dto.response.UserResponseDto;
import com.pragma.users.application.handler.IUserHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRestController {
    private final IUserHandler userHandler;

    @PostMapping("/owner")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> createOwner(@RequestBody @Valid UserRequestDto user) {
        userHandler.createOwner(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userHandler.getAllUsers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'EMPLEADO')")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userHandler.getUserById(id));
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> updateUser(@RequestBody UserRequestDto user) {
        userHandler.updateUser(user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userHandler.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/owner/check/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Boolean> isOwner(@PathVariable Long id) {
        return ResponseEntity.ok(userHandler.isOwner(id));
    }

    @PostMapping("/employee")
    @PreAuthorize("hasRole('PROPIETARIO')")
    public ResponseEntity<Void> createEmployee(
            @RequestBody @Valid UserRequestDto user,
            @AuthenticationPrincipal CustomUserDetails authUser
    ) {
        userHandler.createEmployee(user, authUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
