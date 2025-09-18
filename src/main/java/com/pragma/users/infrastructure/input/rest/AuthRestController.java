package com.pragma.users.infrastructure.input.rest;

import com.pragma.users.application.dto.request.UserRequestDto;
import com.pragma.users.application.dto.request.auth.LoginRequest;
import com.pragma.users.application.dto.response.auth.TokenResponse;
import com.pragma.users.application.handler.IAuthHandler;
import com.pragma.users.application.handler.IUserHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthRestController {
    private final IAuthHandler authHandler;
    private final IUserHandler userHandler;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        final TokenResponse token = authHandler.login(loginRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register/client")
    public ResponseEntity<Void> createClient(@RequestBody @Valid UserRequestDto user) {
        userHandler.createClient(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
