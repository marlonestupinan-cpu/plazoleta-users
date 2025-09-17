package com.pragma.users.application.handler;

import com.pragma.users.application.dto.request.auth.LoginRequest;
import com.pragma.users.application.dto.response.auth.TokenResponse;

public interface IAuthHandler {
    TokenResponse login(LoginRequest loginRequest);
}
