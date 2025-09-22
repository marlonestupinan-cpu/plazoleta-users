package com.pragma.users.domain.api;

public interface IPasswordEncoderPort {
    String encode(String rawPassword);
}
