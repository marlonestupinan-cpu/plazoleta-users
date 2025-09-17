package com.pragma.users.application.handler.impl;

import com.pragma.users.application.dto.request.auth.LoginRequest;
import com.pragma.users.application.dto.response.auth.TokenResponse;
import com.pragma.users.application.handler.IAuthHandler;
import com.pragma.users.application.handler.ITokenGenerator;
import com.pragma.users.domain.api.IUserServicePort;
import com.pragma.users.domain.api.auth.ITokenServicePort;
import com.pragma.users.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthHandler implements IAuthHandler {
    private final ITokenServicePort tokenServicePort;
    private final ITokenGenerator tokenGenerator;
    private final AuthenticationManager authenticationManager;

    private final IUserServicePort userServicePort;

    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        User user = userServicePort.getUserByEmail(loginRequest.getEmail());
        String token = tokenGenerator.generateToken(user);
        String refreshToken = tokenGenerator.generateRefreshToken(user);

        tokenServicePort.revokeAllUsersToken(user);
        tokenServicePort.saveToken(user, token);
        return new TokenResponse(token, refreshToken);
    }
}
