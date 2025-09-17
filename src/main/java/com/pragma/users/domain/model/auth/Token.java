package com.pragma.users.domain.model.auth;

import com.pragma.users.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {
    public enum TokenType {
        BEARER
    }
    private String id;
    private String token;
    private Token.TokenType tokenType =  Token.TokenType.BEARER;
    private boolean revoked;
    private boolean expired;
    private User user;
}
