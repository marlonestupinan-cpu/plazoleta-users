package com.pragma.users.domain.api.auth;

import com.pragma.users.domain.model.User;
import com.pragma.users.domain.model.auth.Token;

public interface ITokenServicePort {
    void saveToken(User user, String token);
    void revokeAllUsersToken(User user);
    Token findByToken(String token);
}
