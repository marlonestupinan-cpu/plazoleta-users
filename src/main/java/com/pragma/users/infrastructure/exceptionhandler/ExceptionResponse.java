package com.pragma.users.infrastructure.exceptionhandler;

public enum ExceptionResponse {
    NO_DATA_FOUND("No data found for the requested petition"),
    ILLEGAL_USER_AGE("El usuario debe ser mayor de edad"),
    USER_NOT_FOUND("Usuario no encontrado");

    private final String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}